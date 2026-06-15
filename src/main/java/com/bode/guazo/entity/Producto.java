package com.bode.guazo.entity;

import com.bode.guazo.entity.enums.Categoria;
import com.bode.guazo.entity.enums.ProductoEstado;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "productos")
@Schema(description = "Producto del catálogo de la tienda")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del producto", example = "1")
    private Integer id;

    @Schema(description = "Nombre del producto", example = "Arroz Diana 1kg")
    private String nombre;

    @Column(name = "urlimagen")
    @Schema(description = "URL de la imagen en Cloudinary", example = "https://res.cloudinary.com/demo/image/upload/arroz.jpg")
    private String imgUrl;

    @Schema(description = "Descripción del producto", example = "Arroz blanco de primera calidad")
    private String descripcion;

    @Schema(description = "Precio unitario", example = "2.50")
    private Double precio;

    @Schema(description = "Unidades disponibles en inventario", example = "100")
    private Integer cantidad;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado de inventario: ABUNDANTE (>10), ESCASO (≤10), VACIO (0)", example = "ABUNDANTE")
    private ProductoEstado estado;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Categoría del producto", example = "GRANO")
    private Categoria categoria;

    public Producto() {}

    public Producto(String nombre, String imgUrl, String descripcion, Double precio, Integer cantidad) {
        this.nombre = nombre;
        this.imgUrl = imgUrl;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public ProductoEstado getEstado() { return estado; }
    public void setEstado(ProductoEstado estado) { this.estado = estado; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }
}
