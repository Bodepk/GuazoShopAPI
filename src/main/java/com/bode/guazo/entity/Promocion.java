package com.bode.guazo.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "promociones")
@Schema(description = "Promoción del carrusel de la tienda")
public class Promocion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la promoción", example = "1")
    private Integer id;

    @Schema(description = "Badge o etiqueta (ej: 🔥 OFERTA ESPECIAL)", example = "🔥 OFERTA ESPECIAL")
    private String badge;

    @Schema(description = "Título principal de la promoción", example = "50% OFF en Granos Seleccionados")
    private String titulo;

    @Schema(description = "Descripción corta", example = "Frijoles, lentejas y garbanzos de primera calidad")
    private String descripcion;

    @Schema(description = "URL de la imagen (Cloudinary)", example = "https://res.cloudinary.com/demo/image/upload/promo.jpg")
    @Column(name = "url_imagen")
    private String urlImagen;

    @Schema(description = "Texto del botón de acción", example = "Aprovecha ahora →")
    @Column(name = "texto_boton")
    private String textoBoton;

    @Schema(description = "Fecha y hora de vencimiento de la promoción")
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;

    @Schema(description = "Indica si la promoción está activa")
    private Boolean activa = true;

    @Schema(description = "Fecha de creación")
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @PrePersist
    private void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        if (this.activa == null) this.activa = true;
    }

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
