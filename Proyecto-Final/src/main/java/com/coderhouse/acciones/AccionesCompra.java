package com.coderhouse.acciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coderhouse.entidades.Compra;
import com.coderhouse.repositorios.Repocompra;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AccionesCompra {

    @Autowired
    private Repocompra compraRepository;

    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    public Optional<Compra> buscarCompraPorId(Long id) {
        return compraRepository.findById(id);
    }

    public Compra realizarCompra(Compra compra) {
        // Verificar si la cantidad comprada es mayor que cero
        if (compra.getCantidadComprada() <= 0) {
            throw new IllegalArgumentException("La cantidad comprada debe ser mayor que cero.");
        }

        // Establecer la fecha de compra y marcar como finalizada
        compra.setFechaCompra(LocalDateTime.now());
        compra.setFinalizada(true);

        // Guardar la compra en la base de datos
        return compraRepository.save(compra);
    }

    public void eliminarCompra(Long id) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        if (optionalCompra.isPresent()) {
            compraRepository.delete(optionalCompra.get());
        } else {
            throw new RuntimeException("Compra no encontrada con id: " + id);
        }
    }

    public void cancelarCompra(Long id, String motivo) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        if (optionalCompra.isPresent()) {
            Compra compra = optionalCompra.get();
            if (!compra.isFinalizada() && !compra.isCancelada()) {
                compra.setCancelada(true);
                compra.setFechaCancelacion(LocalDateTime.now());
                compra.setMotivoCancelacion(motivo);
                compraRepository.save(compra);
            } else {
                throw new RuntimeException("La compra no se puede cancelar.");
            }
        } else {
            throw new RuntimeException("Compra no encontrada con id: " + id);
        }
    }

    public double obtenerTotalCompra(Long id) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        if (optionalCompra.isPresent()) {
            return optionalCompra.get().getTotal();
        } else {
            throw new RuntimeException("Compra no encontrada con id: " + id);
        }
    }

    public String detalleCompra(Long id) {
        Optional<Compra> optionalCompra = compraRepository.findById(id);
        if (optionalCompra.isPresent()) {
            Compra compra = optionalCompra.get();
            // Construir y devolver un detalle de la compra como una cadena
            return "Detalle de la compra:\n" +
                    "ID: " + compra.getId() + "\n" +
                    "Cliente: " + compra.getCliente().getNombre() + " " + compra.getCliente().getApellido() + "\n" +
                    "Producto: " + compra.getProducto().getNombre() + "\n" +
                    "Cantidad: " + compra.getCantidadComprada() + "\n" +
                    "Total: " + compra.getTotal() + "\n" +
                    "Fecha de compra: " + compra.getFechaCompra() + "\n" +
                    "Finalizada: " + compra.isFinalizada() + "\n" +
                    "Cancelada: " + compra.isCancelada() + "\n" +
                    "Fecha de cancelación: " + compra.getFechaCancelacion() + "\n" +
                    "Motivo de cancelación: " + compra.getMotivoCancelacion();
        } else {
            throw new RuntimeException("Compra no encontrada con id: " + id);
        }
    }
}


