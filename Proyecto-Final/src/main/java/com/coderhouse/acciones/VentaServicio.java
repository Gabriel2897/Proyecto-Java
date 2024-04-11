package com.coderhouse.acciones;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.coderhouse.entidades.DetalleVenta;
import com.coderhouse.entidades.Venta;
import com.coderhouse.repositorios.RepoVenta;

import java.util.List;
import java.util.Optional;

@Service
public class VentaServicio {

    @Autowired
    private RepoVenta ventaRepository;

    @Transactional(readOnly = true)
    public List<Venta> getAllVentas() {
        List<Venta> ventas = ventaRepository.findAll();
        for (Venta venta : ventas) {
            Hibernate.initialize(venta.getDetalles()); 
        }
        return ventas;
    }

    @Transactional(readOnly = true)
    public Optional<Venta> getVentaById(Long id) {
        Optional<Venta> ventaOptional = ventaRepository.findById(id);
        ventaOptional.ifPresent(venta -> Hibernate.initialize(venta.getDetalles()));
        return ventaOptional;
    }

    public Venta crearVenta(Venta venta) {
        // Calcula el total de la venta sumando los precios de los productos multiplicados por las cantidades vendidas
        double totalVenta = 0.0;
        List<DetalleVenta> detallesVenta = venta.getDetalles();
        for (DetalleVenta detalle : detallesVenta) {
            totalVenta += detalle.getProducto().getPrecio() * detalle.getCantidad();
        }
        
        // Establece el total de la venta
        venta.setTotal(totalVenta);
        
        // Guarda la venta en la base de datos
        return ventaRepository.save(venta);
    }
    @Transactional
    public Venta actualizarVenta(Long id, Venta ventaActualizada) {
        // Verificar si la venta existente está presente en la base de datos
        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        // Actualizar los detalles de la venta existente con los datos de la venta actualizada
        ventaExistente.setFecha(ventaActualizada.getFecha());
        
        // Agregar nuevos detalles de venta a la venta existente (si los hay)
        for (DetalleVenta nuevoDetalle : ventaActualizada.getDetalles()) {
            ventaExistente.agregarDetalleVenta(nuevoDetalle); 
        }
        
        // Eliminar detalles de venta de la venta existente (si es necesario)
        for (DetalleVenta detalleEliminar : ventaActualizada.getDetalles()) {
            ventaExistente.eliminarDetalleVenta(detalleEliminar);
        }

        // Recalcular el total de la venta actualizada
        double totalVenta = calcularTotalVenta(ventaExistente.getDetalles());
        ventaExistente.setTotal(totalVenta);

        // Guardar la venta actualizada en la base de datos
        return ventaRepository.save(ventaExistente);
    }

    private double calcularTotalVenta(List<DetalleVenta> detallesVenta) {
        double totalVenta = 0.0;
        for (DetalleVenta detalle : detallesVenta) {
            totalVenta += detalle.getProducto().getPrecio() * detalle.getCantidad();
        }
        return totalVenta;
    }

    @Transactional
    public void eliminarVenta(Long id) {
       
        Venta ventaExistente = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada con ID: " + id));

        // Eliminar la venta de la base de datos
        ventaRepository.delete(ventaExistente);
    }
}
