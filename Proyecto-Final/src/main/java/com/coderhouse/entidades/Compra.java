package com.coderhouse.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Compra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private Producto producto;

    @Column(nullable = false)
    private int cantidadComprada;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private boolean finalizada;
    
    @Column(nullable = false)
    private boolean cancelada;

    private LocalDateTime fechaCancelacion;

    private String motivoCancelacion;

    public Compra() {
    }

    public Compra(Cliente cliente, Producto producto, int cantidadComprada, double total, LocalDateTime fechaCompra) {
        this.cliente = cliente;
        this.producto = producto;
        this.cantidadComprada = cantidadComprada;
        this.total = total;
        this.fechaCompra = fechaCompra;
        this.finalizada = false;
        this.cancelada = false; 
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidadComprada() {
        return cantidadComprada;
    }

    public void setCantidadComprada(int cantidadComprada) {
        this.cantidadComprada = cantidadComprada;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public boolean isFinalizada() {
        return finalizada;
    }

    public void setFinalizada(boolean finalizada) {
        this.finalizada = finalizada;
    }
    
    public boolean isCancelada() { 
        return cancelada;
    }

    public void setCancelada(boolean cancelada) { 
        this.cancelada = cancelada;
    }

    public LocalDateTime getFechaCancelacion() { 
        return fechaCancelacion;
    }

    public void setFechaCancelacion(LocalDateTime fechaCancelacion) { 
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getMotivoCancelacion() { 
        return motivoCancelacion;
    }

    public void setMotivoCancelacion(String motivoCancelacion) {
        this.motivoCancelacion = motivoCancelacion;
    }
}

