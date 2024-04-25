package com.coderhouse.entidades;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Schema(description = "Modelo de ItemVenta")
public class ItemVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del ítem de venta")
    private Long id;

    @ManyToOne
    @Schema(description = "Producto asociado al ítem de venta")
    private Producto producto;

    @Schema(description = "Cantidad del producto vendido")
    private int cantidad;

    @Schema(description = "Precio unitario del producto vendido")
    private double precioUnitario;

    @Schema(description = "Total del ítem de venta")
    private double total;

    // Constructor
    public ItemVenta() {
    }
    public ItemVenta(Producto producto, int cantidad, double subtotal) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = producto.getPrecio(); // Precio unitario obtenido del producto
        this.total = subtotal; // Total es el subtotal para este ítem
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ItemVenta{" +
                "id=" + id +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", precioUnitario=" + precioUnitario +
                ", total=" + total +
                '}';
    }
}
