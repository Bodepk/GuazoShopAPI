package com.bode.guazo.service;

import com.bode.guazo.dto.CompraProductoDTO;
import com.bode.guazo.dto.ReporteComprasDTO;
import com.bode.guazo.entity.*;
import com.bode.guazo.entity.enums.CompraEstado;
import com.bode.guazo.exception.BusinessException;
import com.bode.guazo.exception.ResourceNotFoundException;
import com.bode.guazo.repository.CompraProductoRepository;
import com.bode.guazo.repository.CompraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CompraProductoRepository cpRepository;

    @Autowired
    private WhatsAppService whatsAppService;

    // ─── Crear compra ─────────────────────────────────────────────────────────

    @Transactional
    public Compra crearCompra(String nombreCliente, String numeroCliente, String direccion,
                              List<CompraProductoDTO> items) {
        Cliente cliente = clienteService.obtenerCliente(nombreCliente, numeroCliente);
        Compra compra = new Compra();
        compra.setDireccion(direccion);
        compra.setNombreCliente(cliente.getNombre());
        compra.setEstado(CompraEstado.PENDIENTE);
        compra.setFecha(LocalDateTime.now());
        compra.setCliente(cliente);

        List<CompraProductos> productosCompra = buildItems(compra, items);
        compra.setProductos(productosCompra);

        Compra guardada = compraRepository.save(compra);
        fixIds(guardada, productosCompra);
        descontarStock(productosCompra);

        // Notificar por WhatsApp (no bloquea si falla)
        whatsAppService.notificarNuevaCompra(guardada);

        return guardada;
    }

    // ─── Consultas ────────────────────────────────────────────────────────────

    public List<Compra> getComprasPorCliente(String numeroTelefono) {
        List<Compra> compras = compraRepository.findByClienteNumero(numeroTelefono);
        if (compras.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron compras para el número: " + numeroTelefono);
        }
        return compras;
    }

    public ReporteComprasDTO getReportePorFechas(LocalDateTime inicio, LocalDateTime fin) {
        if (inicio.isAfter(fin)) {
            throw new BusinessException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        List<Compra> compras = compraRepository.findByFechaBetween(inicio, fin);
        List<Object[]> rawVendidos = cpRepository.findProductosMasVendidos(inicio, fin);

        // Armar resumen de compras
        List<ReporteComprasDTO.CompraResumenDTO> resumenes = compras.stream().map(c -> {
            ReporteComprasDTO.CompraResumenDTO r = new ReporteComprasDTO.CompraResumenDTO();
            r.setId(c.getId());
            r.setNombreCliente(c.getNombreCliente());
            r.setDireccion(c.getDireccion());
            r.setEstado(c.getEstado().name());
            r.setFecha(c.getFecha());
            r.setTotal(c.getProductos().stream()
                    .mapToDouble(cp -> cp.getSubTotal() != null ? cp.getSubTotal() : 0)
                    .sum());
            return r;
        }).toList();

        // Armar top productos
        List<ReporteComprasDTO.ProductoMasVendidoDTO> topProductos = rawVendidos.stream()
                .map(row -> new ReporteComprasDTO.ProductoMasVendidoDTO(
                        (Integer) row[0],
                        (String) row[1],
                        ((Long) row[2]).intValue(),
                        (Double) row[3]
                )).toList();

        double totalGanancias = resumenes.stream()
                .mapToDouble(r -> r.getTotal() != null ? r.getTotal() : 0)
                .sum();

        ReporteComprasDTO reporte = new ReporteComprasDTO();
        reporte.setFechaInicio(inicio);
        reporte.setFechaFin(fin);
        reporte.setTotalCompras(compras.size());
        reporte.setTotalGanancias(Math.round(totalGanancias * 100.0) / 100.0);
        reporte.setCompras(resumenes);
        reporte.setProductosMasVendidos(topProductos);

        return reporte;
    }

    // ─── Actualizar estado ────────────────────────────────────────────────────

    @Transactional
    public Compra actualizarEstado(Integer compraId, CompraEstado nuevoEstado) {
        Compra compra = compraRepository.findById(compraId)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + compraId));

        if (compra.getEstado() == CompraEstado.COMPLETA && nuevoEstado == CompraEstado.CANCELADA) {
            throw new BusinessException("No se puede cancelar una compra ya entregada");
        }

        compra.setEstado(nuevoEstado);
        return compraRepository.save(compra);
    }

    // ─── Helpers privados ─────────────────────────────────────────────────────

    private List<CompraProductos> buildItems(Compra compra, List<CompraProductoDTO> items) {
        List<CompraProductos> lista = new ArrayList<>();
        for (CompraProductoDTO item : items) {
            Producto producto = productService.obtenerProducto(item.getProductoId());
            productService.validarStock(producto.getId(), item.getCantidad());

            CompraProductos cp = new CompraProductos();
            cp.setCompra(compra);
            cp.setProducto(producto);
            cp.setCantidad(item.getCantidad());
            cp.setPrecio(item.getPrecioUnitario());
            cp.setSubTotal(item.getCantidad() * item.getPrecioUnitario());
            lista.add(cp);
        }
        return lista;
    }

    private void fixIds(Compra guardada, List<CompraProductos> items) {
        for (CompraProductos cp : items) {
            cp.getId().setCompraId(guardada.getId());
            cp.getId().setProductoId(cp.getProducto().getId());
        }
    }

    private void descontarStock(List<CompraProductos> items) {
        for (CompraProductos cp : items) {
            productService.actualizarStock(cp.getProducto().getId(), cp.getCantidad());
        }
    }
}
