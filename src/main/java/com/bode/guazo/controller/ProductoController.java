package com.bode.guazo.controller;

import com.bode.guazo.dto.ProductoDTO;
import com.bode.guazo.entity.enums.Categoria;
import com.bode.guazo.entity.enums.ProductoEstado;
import com.bode.guazo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
@Tag(name = "Productos", description = "Gestión del catálogo de productos de la tienda")
public class ProductoController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Crear producto", description = "Registra un nuevo producto en el inventario")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Producto creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<ProductoDTO> crearProducto(@Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.crearProducto(dto));
    }

    @Operation(summary = "Listar todos los productos", description = "Devuelve el catálogo completo de productos")
    @ApiResponse(responseCode = "200", description = "Lista de productos")
    @GetMapping
    public ResponseEntity<List<ProductoDTO>> getAll() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @Operation(summary = "Obtener producto por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto encontrado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductoDTO> getById(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductoDTO(id));
    }

    @Operation(summary = "Filtrar por categoría",
               description = "Valores válidos: ELECTRODOMESTICO, BEBIDA, GRANO, ENLATADO, CARNICO, CONDIMENTO, CONFITURA, HERRAMIENTA, ELABORADO")
    @ApiResponse(responseCode = "200", description = "Productos filtrados por categoría")
    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<ProductoDTO>> getPorCategoria(
            @Parameter(description = "Categoría del producto", example = "GRANO") @PathVariable Categoria categoria) {
        return ResponseEntity.ok(productService.getPorCategoria(categoria));
    }

    @Operation(summary = "Filtrar por estado",
               description = "Valores válidos: ABUNDANTE, ESCASO, VACIO")
    @ApiResponse(responseCode = "200", description = "Productos filtrados por estado")
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ProductoDTO>> getPorEstado(
            @Parameter(description = "Estado del producto", example = "ABUNDANTE") @PathVariable ProductoEstado estado) {
        return ResponseEntity.ok(productService.getPorEstado(estado));
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza todos los campos del producto")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Producto actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizar(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Integer id,
            @Valid @RequestBody ProductoDTO dto) {
        return ResponseEntity.ok(productService.actualizarProducto(id, dto));
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto del catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Producto eliminado"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(
            @Parameter(description = "ID del producto", example = "1") @PathVariable Integer id) {
        productService.borrarProducto(id);
        return ResponseEntity.noContent().build();
    }
}
