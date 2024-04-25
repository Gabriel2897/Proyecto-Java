package com.coderhouse.entidades;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity(name = "venta")
@Schema(description = "Modelo de Venta")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la venta")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    @Schema(description = "Cliente asociado a la venta")
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "venta_id")
    @Schema(description = "Items de venta asociados a la venta")
    private List<ItemVenta> items;

    @Temporal(TemporalType.TIMESTAMP)
    @Schema(description = "Fecha de la venta")
    private Date fecha;

    @Schema(description = "Total de la venta")
    private double totalVenta;

    // Constructor
    public Venta() {
    }
    
    public Venta(Cliente cliente, List<ItemVenta> items, double totalVenta) {
        this.cliente = cliente;
        this.items = items;
        this.totalVenta = totalVenta;
    }


    // Getters y Setters
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

    public List<ItemVenta> getItems() {
        return items;
    }

    public void setItems(List<ItemVenta> items) {
        this.items = items;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", items=" + items +
                ", fecha=" + fecha +
                ", totalVenta=" + totalVenta +
                '}';
    }
}
