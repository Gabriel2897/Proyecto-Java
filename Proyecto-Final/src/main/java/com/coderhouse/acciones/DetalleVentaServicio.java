package com.coderhouse.acciones;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entidades.DetalleVenta;
import com.coderhouse.repositorios.RepoDetalleVenta;

import java.util.List;
import java.util.Optional;

@Service
public class DetalleVentaServicio {

    @Autowired
    private RepoDetalleVenta detalleVentaRepository;

    public List<DetalleVenta> getAllDetallesVenta() {
        List<DetalleVenta> detalles = detalleVentaRepository.findAll();
        detalles.forEach(detalleVenta -> Hibernate.initialize(detalleVenta.getVenta()));
        return detalles;
    }


    public Optional<DetalleVenta> getDetalleVentaById(Long id) {
        Optional<DetalleVenta> detalleVentaOptional = detalleVentaRepository.findById(id);
        detalleVentaOptional.ifPresent(detalleVenta -> Hibernate.initialize(detalleVenta.getVenta()));
        return detalleVentaOptional;
    }

    public DetalleVenta crearDetalleVenta(DetalleVenta detalleVenta) {
        return detalleVentaRepository.save(detalleVenta);
    }

    public DetalleVenta actualizarDetalleVenta(Long id, DetalleVenta detalleVenta) {
        DetalleVenta detalleVentaExistente = detalleVentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Detalle de venta no encontrado con ID: " + id));

        // Actualizar los datos del detalle de venta existente con los datos proporcionados
        detalleVentaExistente.setProducto(detalleVenta.getProducto());
        detalleVentaExistente.setCantidad(detalleVenta.getCantidad());

        return detalleVentaRepository.save(detalleVentaExistente);
    }

    public void eliminarDetalleVenta(Long id) {
        detalleVentaRepository.deleteById(id);
    }
}