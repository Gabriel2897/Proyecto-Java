package com.coderhouse.entidades;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity(name = "producto")
@Schema(description = "Modelo de Producto") 
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del producto")
    private Long id;

    @Schema(description = "Nombre del producto")
    private String producto;

    @Schema(description = "Precio del producto")
    private double precio;

    @Schema(description = "Cantidad disponible del producto")
    private int cantidad;

    // Constructor
    public Producto() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProducto() { 
        return producto;
    }

    public void setProducto(String producto) { 
        this.producto = producto;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               ", Producto: " + producto +
               ", Precio: " + precio +
               ", Cantidad: " + cantidad;
    }
}
