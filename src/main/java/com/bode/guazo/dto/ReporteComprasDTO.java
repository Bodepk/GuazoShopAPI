package com.bode.guazo.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Reporte de compras entre dos fechas")
public class ReporteComprasDTO {

    @Schema(description = "Fecha de inicio del periodo")
    private LocalDateTime fechaInicio;

    @Schema(description = "Fecha de fin del periodo")
    private LocalDateTime fechaFin;

    @Schema(description = "Total de compras en el periodo")
    private Integer totalCompras;

    @Schema(description = "Ganancias totales del periodo")
    private Double totalGanancias;

    @Schema(description = "Lista de compras en el periodo")
    private List<CompraResumenDTO> compras;

    @Schema(description = "Productos más vendidos en el periodo")
    private List<ProductoMasVendidoDTO> productosMasVendidos;

    public ReporteComprasDTO() {}

    public LocalDateTime getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDateTime fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDateTime getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDateTime fechaFin) { this.fechaFin = fechaFin; }

    public Integer getTotalCompras() { return totalCompras; }
    public void setTotalCompras(Integer totalCompras) { this.totalCompras = totalCompras; }

    public Double getTotalGanancias() { return totalGanancias; }
    public void setTotalGanancias(Double totalGanancias) { this.totalGanancias = totalGanancias; }

    public List<CompraResumenDTO> getCompras() { return compras; }
    public void setCompras(List<CompraResumenDTO> compras) { this.compras = compras; }

    public List<ProductoMasVendidoDTO> getProductosMasVendidos() { return productosMasVendidos; }
    public void setProductosMasVendidos(List<ProductoMasVendidoDTO> productosMasVendidos) { this.productosMasVendidos = productosMasVendidos; }

    // ─── Clases internas ───────────────────────────────────────────────────────

    @Schema(description = "Resumen de una compra")
    public static class CompraResumenDTO {
        private Integer id;
        private String nombreCliente;
        private String direccion;
        private String estado;
        private LocalDateTime fecha;
        private Double total;

        public CompraResumenDTO() {}

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getNombreCliente() { return nombreCliente; }
        public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }

        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        public LocalDateTime getFecha() { return fecha; }
        public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }

        public Double getTotal() { return total; }
        public void setTotal(Double total) { this.total = total; }
    }

    @Schema(description = "Producto más vendido con estadísticas")
    public static class ProductoMasVendidoDTO {
        private Integer productoId;
        private String nombre;
        private Integer cantidadVendida;
        private Double totalGenerado;

        public ProductoMasVendidoDTO() {}

        public ProductoMasVendidoDTO(Integer productoId, String nombre, Integer cantidadVendida, Double totalGenerado) {
            this.productoId = productoId;
            this.nombre = nombre;
            this.cantidadVendida = cantidadVendida;
            this.totalGenerado = totalGenerado;
        }

        public Integer getProductoId() { return productoId; }
        public void setProductoId(Integer productoId) { this.productoId = productoId; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public Integer getCantidadVendida() { return cantidadVendida; }
        public void setCantidadVendida(Integer cantidadVendida) { this.cantidadVendida = cantidadVendida; }

        public Double getTotalGenerado() { return totalGenerado; }
        public void setTotalGenerado(Double totalGenerado) { this.totalGenerado = totalGenerado; }
    }
}
