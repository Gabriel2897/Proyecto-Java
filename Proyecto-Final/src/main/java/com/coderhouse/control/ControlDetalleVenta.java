package com.coderhouse.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.acciones.DetalleVentaServicio;
import com.coderhouse.entidades.DetalleVenta;

@RestController
@RequestMapping("/detalles-venta")
public class ControlDetalleVenta {

    @Autowired
    private DetalleVentaServicio detalleVentaService;

    @GetMapping
    public List<DetalleVenta> getAllDetallesVenta() {
        return detalleVentaService.getAllDetallesVenta();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> getDetalleVentaById(@PathVariable Long id) {
        Optional<DetalleVenta> detalleVenta = detalleVentaService.getDetalleVentaById(id);
        if (detalleVenta.isPresent()) {
            return ResponseEntity.ok(detalleVenta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<DetalleVenta> crearDetalleVenta(@RequestBody DetalleVenta detalleVenta) {
        DetalleVenta nuevoDetalleVenta = detalleVentaService.crearDetalleVenta(detalleVenta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoDetalleVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleVenta> actualizarDetalleVenta(@PathVariable Long id, @RequestBody DetalleVenta detalleVenta) {
        DetalleVenta detalleVentaActualizado = detalleVentaService.actualizarDetalleVenta(id, detalleVenta);
        return ResponseEntity.ok(detalleVentaActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarDetalleVenta(@PathVariable Long id) {
        detalleVentaService.eliminarDetalleVenta(id);
        return ResponseEntity.noContent().build();
    }
}
