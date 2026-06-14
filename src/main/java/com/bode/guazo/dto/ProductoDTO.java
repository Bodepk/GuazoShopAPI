package com.bode.guazo.dto;

import com.bode.guazo.entity.enums.Categoria;
import com.bode.guazo.entity.enums.ProductoEstado;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Datos del producto")
public class ProductoDTO {

    @Schema(description = "ID del producto (solo en respuestas)", example = "1")
    private Integer id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del producto", example = "Arroz Diana 1kg")
    private String name;

    @Schema(description = "URL de la imagen del producto", example = "https://example.com/img/arroz.jpg")
    private String urlImg;

    @Schema(description = "Descripción del producto", example = "Arroz blanco de primera calidad")
    private String description;

    @NotNull(message = "El precio es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Schema(description = "Precio unitario", example = "2.50")
    private Double price;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad debe ser mayor o igual a 0")
    @Schema(description = "Cantidad disponible en inventario", example = "100")
    private Integer quantity;

    @Schema(description = "Estado del producto", example = "ABUNDANTE")
    private ProductoEstado productEstado;

    @Schema(description = "Categoría del producto", example = "GRANO")
    private Categoria productCategoria;

    public ProductoDTO() {}

    public ProductoDTO(Integer id, String name, String urlImg, String description,
                       Double price, Integer quantity, ProductoEstado productEstado, Categoria productCategoria) {
        this.id = id;
        this.name = name;
        this.urlImg = urlImg;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.productEstado = productEstado;
        this.productCategoria = productCategoria;
    }

    public ProductoDTO(String name, String urlImg, String description, Double price,
                       Integer quantity, ProductoEstado productEstado, Categoria productCategoria) {
        this.name = name;
        this.urlImg = urlImg;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
        this.productEstado = productEstado;
        this.productCategoria = productCategoria;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getUrlImg() { return urlImg; }
    public void setUrlImg(String urlImg) { this.urlImg = urlImg; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public ProductoEstado getProductEstado() { return productEstado; }
    public void setProductEstado(ProductoEstado productEstado) { this.productEstado = productEstado; }

    public Categoria getProductCategoria() { return productCategoria; }
    public void setProductCategoria(Categoria productCategoria) { this.productCategoria = productCategoria; }
}
