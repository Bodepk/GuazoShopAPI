package com.bode.guazo.controller;

import com.bode.guazo.dto.PromocionDTO;
import com.bode.guazo.service.PromocionService;
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
@RequestMapping("/promocion")
@Tag(name = "Promociones", description = "Gestión del carrusel de promociones. Los endpoints públicos los consume el frontend; los de gestión los usa el admin.")
public class PromocionController {

    @Autowired
    private PromocionService promocionService;

    // ─── Endpoints públicos (frontend web) ───────────────────────────────────

    @Operation(
        summary = "Listar promociones vigentes",
        description = "Devuelve solo las promociones activas y no vencidas. Usado por el frontend del cliente."
    )
    @ApiResponse(responseCode = "200", description = "Lista de promociones vigentes")
    @GetMapping("/vigentes")
    public ResponseEntity<List<PromocionDTO>> getVigentes() {
        return ResponseEntity.ok(promocionService.getPromocionesVigentes());
    }

    // ─── Endpoints de administración (app de escritorio) ──────────────────────

    @Operation(summary = "Listar todas las promociones", description = "Incluye activas, inactivas y vencidas. Solo para el admin.")
    @ApiResponse(responseCode = "200", description = "Lista completa de promociones")
    @GetMapping
    public ResponseEntity<List<PromocionDTO>> getAll() {
        return ResponseEntity.ok(promocionService.getTodasPromociones());
    }

    @Operation(summary = "Obtener promoción por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Promoción encontrada"),
        @ApiResponse(responseCode = "404", description = "Promoción no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PromocionDTO> getById(
            @Parameter(description = "ID de la promoción") @PathVariable Integer id) {
        return ResponseEntity.ok(promocionService.getById(id));
    }

    @Operation(summary = "Crear promoción", description = "Crea una nueva promoción para el carrusel. La imagen debe ser una URL de Cloudinary.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Promoción creada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<PromocionDTO> crear(@Valid @RequestBody PromocionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(promocionService.crear(dto));
    }

    @Operation(summary = "Actualizar promoción")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Promoción actualizada"),
        @ApiResponse(responseCode = "404", description = "Promoción no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PromocionDTO> actualizar(
            @Parameter(description = "ID de la promoción") @PathVariable Integer id,
            @Valid @RequestBody PromocionDTO dto) {
        return ResponseEntity.ok(promocionService.actualizar(id, dto));
    }

    @Operation(summary = "Activar / desactivar promoción", description = "Alterna el estado activo de la promoción sin eliminarla.")
    @ApiResponse(responseCode = "200", description = "Estado alternado")
    @PatchMapping("/{id}/toggle")
    public ResponseEntity<PromocionDTO> toggle(
            @Parameter(description = "ID de la promoción") @PathVariable Integer id) {
        return ResponseEntity.ok(promocionService.toggleActiva(id));
    }

    @Operation(summary = "Eliminar promoción")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Promoción eliminada"),
        @ApiResponse(responseCode = "404", description = "Promoción no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @Parameter(description = "ID de la promoción") @PathVariable Integer id) {
        promocionService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
