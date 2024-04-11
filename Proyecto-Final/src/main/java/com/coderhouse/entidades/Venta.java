package com.coderhouse.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;

    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
    private List<DetalleVenta> detalles;

    private double total;

    // Constructor, getters y setters

    public Venta() {
        this.fecha = new Date();
    }

    public Venta(Cliente cliente) {
        this.cliente = cliente;
        this.fecha = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    // Método para calcular y actualizar el total de la venta
    public void actualizarTotalVenta() {
        double totalVenta = 0.0;
        for (DetalleVenta detalle : detalles) {
            totalVenta += detalle.getCantidad() * detalle.getPrecioUnitario();
        }
        this.total = totalVenta;
    }
    public void agregarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalles == null) {
            detalles = new ArrayList<>();
        }
        detalles.add(detalleVenta);
        detalleVenta.setVenta(this); 
        actualizarTotalVenta();
    }

    public void eliminarDetalleVenta(DetalleVenta detalleVenta) {
        if (detalles != null) {
            detalles.remove(detalleVenta);
            detalleVenta.setVenta(null); 
            actualizarTotalVenta();
        }
    }
}
