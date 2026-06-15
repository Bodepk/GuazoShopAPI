package com.bode.guazo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")
@Schema(description = "Cliente de la tienda")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID del cliente", example = "1")
    private Integer id;

    @Schema(description = "Nombre del cliente (en minúsculas)", example = "juan pérez")
    private String nombre;

    @Schema(description = "Número de teléfono — identificador único del cliente", example = "04141234567")
    private String numero;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Compra> compras;

    public Cliente() {}

    public Cliente(String nombre, String numero) {
        this.nombre = nombre;
        this.numero = numero;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public List<Compra> getCompras() { return compras; }
    public void setCompras(List<Compra> compras) { this.compras = compras; }
}
