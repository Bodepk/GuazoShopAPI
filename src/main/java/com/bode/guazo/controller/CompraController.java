package com.bode.guazo.controller;

import com.bode.guazo.dto.CompraRequestDTO;
import com.bode.guazo.dto.ReporteComprasDTO;
import com.bode.guazo.entity.Compra;
import com.bode.guazo.entity.enums.CompraEstado;
import com.bode.guazo.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/compra")
@Tag(name = "Compras", description = "Gestión de compras y pedidos")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @Operation(summary = "Crear compra",
               description = "Registra una nueva compra. Si el cliente no existe se crea automáticamente usando el número de teléfono como identificador único.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Compra creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos o stock insuficiente"),
            @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })
    @PostMapping
    public ResponseEntity<Compra> crearCompra(@Valid @RequestBody CompraRequestDTO request) {
        Compra compra = compraService.crearCompra(
                request.getNombreCliente(),
                request.getNumeroCliente(),
                request.getDireccion(),
                request.getItems()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(compra);
    }

    @Operation(summary = "Compras por cliente",
               description = "Devuelve todas las compras de un cliente buscando por su número de teléfono")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de compras del cliente"),
            @ApiResponse(responseCode = "404", description = "No se encontraron compras para ese número")
    })
    @GetMapping("/cliente/{numero}")
    public ResponseEntity<List<Compra>> getComprasPorCliente(
            @Parameter(description = "Número de teléfono del cliente", example = "04141234567")
            @PathVariable String numero) {
        return ResponseEntity.ok(compraService.getComprasPorCliente(numero));
    }

    @Operation(summary = "Reporte por rango de fechas",
               description = "Devuelve todas las compras entre dos fechas, junto con el total de ganancias y los productos más vendidos del periodo. Formato de fecha: `yyyy-MM-dd'T'HH:mm:ss`")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reporte generado"),
            @ApiResponse(responseCode = "400", description = "Rango de fechas inválido")
    })
    @GetMapping("/reporte")
    public ResponseEntity<ReporteComprasDTO> getReportePorFechas(
            @Parameter(description = "Fecha de inicio (ISO 8601)", example = "2025-01-01T00:00:00")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,

            @Parameter(description = "Fecha de fin (ISO 8601)", example = "2025-12-31T23:59:59")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(compraService.getReportePorFechas(inicio, fin));
    }

    @Operation(summary = "Actualizar estado de compra",
               description = "Cambia el estado de una compra. Estados válidos: PENDIENTE, COMPLETA, CANCELADA. Una compra COMPLETA no puede cancelarse.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado actualizado"),
            @ApiResponse(responseCode = "400", description = "Transición de estado inválida"),
            @ApiResponse(responseCode = "404", description = "Compra no encontrada")
    })
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Compra> actualizarEstado(
            @Parameter(description = "ID de la compra", example = "1") @PathVariable Integer id,
            @Parameter(description = "Nuevo estado", example = "COMPLETA") @RequestParam CompraEstado estado) {
        return ResponseEntity.ok(compraService.actualizarEstado(id, estado));
    }
}
