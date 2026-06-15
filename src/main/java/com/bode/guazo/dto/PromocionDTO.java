package com.bode.guazo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "Datos de una promoción del carrusel")
public class PromocionDTO {

    @Schema(description = "ID (solo en respuestas)", example = "1")
    private Integer id;

    @NotBlank(message = "El badge es obligatorio")
    @Schema(description = "Etiqueta de la promoción", example = "🔥 OFERTA ESPECIAL")
    private String badge;

    @NotBlank(message = "El título es obligatorio")
    @Schema(description = "Título principal", example = "50% OFF en Granos")
    private String titulo;

    @Schema(description = "Descripción corta", example = "Frijoles y lentejas de primera calidad")
    private String descripcion;

    @Schema(description = "URL de imagen en Cloudinary")
    private String urlImagen;

    @Schema(description = "Texto del botón", example = "Aprovecha ahora →")
    private String textoBoton;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @Schema(description = "Fecha/hora de vencimiento", example = "2025-12-31T23:59:59")
    private LocalDateTime fechaVencimiento;

    @Schema(description = "¿Está activa?", example = "true")
    private Boolean activa;

    @Schema(description = "Fecha de creación (solo en respuestas)")
    private LocalDateTime fechaCreacion;

    public PromocionDTO() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getBadge() { return badge; }
    public void setBadge(String badge) { this.badge = badge; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrlImagen() { return urlImagen; }
    public void setUrlImagen(String urlImagen) { this.urlImagen = urlImagen; }

    public String getTextoBoton() { return textoBoton; }
    public void setTextoBoton(String textoBoton) { this.textoBoton = textoBoton; }

    public LocalDateTime getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(LocalDateTime fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public Boolean getActiva() { return activa; }
    public void setActiva(Boolean activa) { this.activa = activa; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
