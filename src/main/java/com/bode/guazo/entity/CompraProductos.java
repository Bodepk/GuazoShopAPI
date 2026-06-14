package com.bode.guazo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "compra_producto")
@Schema(description = "Producto dentro de una compra con cantidad y precio al momento de compra")
public class CompraProductos {

    @EmbeddedId
    private CompraProductosPK id = new CompraProductosPK();

    @Schema(description = "Cantidad comprada", example = "2")
    private Integer cantidad;

    @Schema(description = "Precio unitario al momento de la compra", example = "2.50")
    private Double precio;

    @Column(name = "subtotal")
    @Schema(description = "Subtotal (cantidad × precio)", example = "5.00")
    private Double subTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("compraId")
    @JoinColumn(name = "compra_id")
    @JsonIgnoreProperties({"productos", "cliente", "hibernateLazyInitializer"})
    private Compra compra;

    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("productoId")
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    @Schema(description = "Producto comprado")
    private Producto producto;

    @PrePersist
    @PreUpdate
    private void calcularSubTotal() {
        if (cantidad != null && precio != null) {
            this.subTotal = cantidad * precio;
        }
    }

    public CompraProductosPK getId() { return id; }
    public void setId(CompraProductosPK id) { this.id = id; }

    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

    public Double getPrecio() { return precio; }
    public void setPrecio(Double precio) { this.precio = precio; }

    public Double getSubTotal() { return subTotal; }
    public void setSubTotal(Double subTotal) { this.subTotal = subTotal; }

    public Compra getCompra() { return compra; }
    public void setCompra(Compra compra) { this.compra = compra; }

    public Producto getProducto() { return producto; }
    public void setProducto(Producto producto) { this.producto = producto; }
}
