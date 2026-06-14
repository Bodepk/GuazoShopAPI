package com.bode.guazo.entity;

import com.bode.guazo.entity.enums.Categoria;
import com.bode.guazo.entity.enums.ProductoEstado;
import jakarta.persistence.*;

@Entity
@Table(name="productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    @Column(name="urlimagen")
    private String imgUrl;
    private String descripcion;
    private Double precio;
    private Integer cantidad;
    @Enumerated(EnumType.STRING)
    private ProductoEstado estado;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    public Producto() {
    }

    public Producto(String nombre, String imgUrl, String descripcion,Double precio, Integer cantidad) {
        this.nombre = nombre;
        this.imgUrl = imgUrl;
        this.descripcion = descripcion;
        this.precio = precio;
        this.cantidad = cantidad;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoEstado getEstado() {
        return estado;
    }

    public void setEstado(ProductoEstado estado) {
        this.estado = estado;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
