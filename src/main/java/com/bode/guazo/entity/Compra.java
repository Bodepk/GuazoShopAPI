package com.bode.guazo.entity;

import com.bode.guazo.entity.enums.CompraEstado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "compras")
@Schema(description = "Entidad que representa una compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID de la compra", example = "1")
    private Integer id;

    @Schema(description = "Fecha y hora de la compra")
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"compra"})
    @Schema(description = "Productos incluidos en la compra")
    private List<CompraProductos> productos;

    @Column(name = "nombre_cliente")
    @Schema(description = "Nombre del cliente al momento de la compra", example = "juan pérez")
    private String nombreCliente;

    @Schema(description = "Dirección de entrega", example = "Av. Principal #123")
    private String direccion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"compras", "hibernateLazyInitializer"})
    @Schema(description = "Cliente que realizó la compra")
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Estado de la compra", example = "PENDIENTE")
    private CompraEstado estado;

    @Transient
    @Schema(description = "Total de la compra calculado a partir de los subtotales")
    public Double getTotal() {
        if (productos == null) return 0.0;
        return productos.stream()
                .mapToDouble(cp -> cp.getSubTotal() != null ? cp.getSubTotal() : 0)
                .sum();
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

    public List<CompraProductos> getProductos() { return productos; }
    public void setProductos(List<CompraProductos> productos) { this.productos = productos; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public CompraEstado getEstado() { return estado; }
    public void setEstado(CompraEstado estado) { this.estado = estado; }
}
