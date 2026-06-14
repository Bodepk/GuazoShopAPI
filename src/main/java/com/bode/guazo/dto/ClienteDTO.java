package com.bode.guazo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Datos del cliente")
public class ClienteDTO {

    @Schema(description = "ID del cliente (solo en respuestas)", example = "1")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del cliente", example = "Juan Pérez")
    private String nombre;

    @NotBlank(message = "El número de teléfono es obligatorio")
    @Schema(description = "Número de teléfono del cliente", example = "04141234567")
    private String numero;

    public ClienteDTO() {}

    public ClienteDTO(Integer id, String nombre, String numero) {
        this.id = id;
        this.nombre = nombre;
        this.numero = numero;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
}
