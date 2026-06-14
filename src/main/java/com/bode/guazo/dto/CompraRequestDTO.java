package com.bode.guazo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Schema(description = "Request para crear una compra")
public class CompraRequestDTO {

    @NotBlank(message = "El nombre del cliente es obligatorio")
    @Schema(description = "Nombre del cliente", example = "María González")
    private String nombreCliente;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Schema(description = "Número de teléfono del cliente", example = "04141234567")
    private String numeroCliente;

    @NotBlank(message = "La dirección es obligatoria")
    @Schema(description = "Dirección de entrega", example = "Av. Principal #123, Caracas")
    private String direccion;

    @NotEmpty(message = "La compra debe tener al menos un producto")
    @Valid
    @Schema(description = "Lista de productos a comprar")
    private List<CompraProductoDTO> items;

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNumeroCliente() { return numeroCliente; }
    public void setNumeroCliente(String numeroCliente) { this.numeroCliente = numeroCliente; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<CompraProductoDTO> getItems() { return items; }
    public void setItems(List<CompraProductoDTO> items) { this.items = items; }
}
