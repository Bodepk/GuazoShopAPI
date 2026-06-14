package com.bode.guazo.service;

import com.bode.guazo.dto.ProductoDTO;
import com.bode.guazo.entity.Producto;
import com.bode.guazo.entity.enums.Categoria;
import com.bode.guazo.entity.enums.ProductoEstado;
import com.bode.guazo.exception.BusinessException;
import com.bode.guazo.exception.ResourceNotFoundException;
import com.bode.guazo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // ─── Crear ────────────────────────────────────────────────────────────────

    @Transactional
    public ProductoDTO crearProducto(ProductoDTO dto) {
        Producto producto = new Producto();
        mapDtoToEntity(dto, producto);
        producto.setEstado(resolverEstado(dto.getProductEstado(), dto.getQuantity()));
        Producto guardado = productRepository.save(producto);
        return toDto(guardado);
    }

    // ─── Leer ─────────────────────────────────────────────────────────────────

    public List<ProductoDTO> getProducts() {
        return productRepository.findAll().stream()
                .map(this::toDto)
                .toList();
    }

    public ProductoDTO getProductoDTO(Integer id) {
        return toDto(obtenerProducto(id));
    }

    public List<ProductoDTO> getPorCategoria(Categoria categoria) {
        return productRepository.findByCategoria(categoria).stream()
                .map(this::toDto)
                .toList();
    }

    public List<ProductoDTO> getPorEstado(ProductoEstado estado) {
        return productRepository.findByEstado(estado).stream()
                .map(this::toDto)
                .toList();
    }

    // ─── Actualizar ───────────────────────────────────────────────────────────

    @Transactional
    public ProductoDTO actualizarProducto(Integer id, ProductoDTO dto) {
        Producto producto = obtenerProducto(id);
        mapDtoToEntity(dto, producto);
        producto.setEstado(resolverEstado(dto.getProductEstado(), dto.getQuantity()));
        return toDto(productRepository.save(producto));
    }

    // ─── Borrar ───────────────────────────────────────────────────────────────

    @Transactional
    public void borrarProducto(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        productRepository.deleteById(id);
    }

    // ─── Lógica de stock ──────────────────────────────────────────────────────

    public void validarStock(Integer productoId, Integer cant) {
        Producto producto = obtenerProducto(productoId);
        if (producto.getEstado() == ProductoEstado.VACIO) {
            throw new BusinessException("El producto '" + producto.getNombre() + "' está agotado");
        }
        if (cant > producto.getCantidad()) {
            throw new BusinessException(
                    "Stock insuficiente para '" + producto.getNombre() +
                    "'. Disponible: " + producto.getCantidad() + ", Solicitado: " + cant
            );
        }
    }

    @Transactional
    public Producto actualizarStock(Integer productoId, Integer cant) {
        Producto producto = obtenerProducto(productoId);
        int nuevaCantidad = producto.getCantidad() - cant;
        producto.setCantidad(nuevaCantidad);
        producto.setEstado(resolverEstado(null, nuevaCantidad));
        return productRepository.save(producto);
    }

    public Producto obtenerProducto(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private void mapDtoToEntity(ProductoDTO dto, Producto producto) {
        producto.setNombre(dto.getName());
        producto.setImgUrl(dto.getUrlImg());
        producto.setDescripcion(dto.getDescription());
        producto.setPrecio(dto.getPrice());
        producto.setCantidad(dto.getQuantity());
        producto.setCategoria(dto.getProductCategoria());
    }

    private ProductoEstado resolverEstado(ProductoEstado estadoSugerido, Integer cantidad) {
        if (cantidad == null || cantidad == 0) return ProductoEstado.VACIO;
        if (cantidad <= 10) return ProductoEstado.ESCASO;
        if (estadoSugerido != null) return estadoSugerido;
        return ProductoEstado.ABUNDANTE;
    }

    public ProductoDTO toDto(Producto p) {
        return new ProductoDTO(
                p.getId(),
                p.getNombre(),
                p.getImgUrl(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getCantidad(),
                p.getEstado(),
                p.getCategoria()
        );
    }
}
