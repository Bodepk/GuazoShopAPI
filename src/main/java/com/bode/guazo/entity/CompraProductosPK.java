package com.bode.guazo.entity;

import jakarta.persistence.Embeddable;

import java.util.Objects;
@Embeddable
public class CompraProductosPK {

    private Integer productoId;
    private Integer compraId;

    public CompraProductosPK() {
    }

    public CompraProductosPK(Integer productoId, Integer compraId) {
        this.productoId = productoId;
        this.compraId = compraId;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Integer getCompraId() {
        return compraId;
    }

    public void setCompraId(Integer compraId) {
        this.compraId = compraId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CompraProductosPK that = (CompraProductosPK) o;
        return Objects.equals(productoId, that.productoId) && Objects.equals(compraId, that.compraId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productoId, compraId);
    }
}
