package com.coderhouse.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.acciones.VentaServicio;
import com.coderhouse.entidades.Venta;

@RestController
@RequestMapping("/ventas")
public class ControlVenta {

    @Autowired
    private VentaServicio ventaService;

    @GetMapping
    public List<Venta> getAllVentas() {
        return ventaService.getAllVentas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        Optional<Venta> venta = ventaService.getVentaById(id);
        if (venta.isPresent()) {
            return ResponseEntity.ok(venta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
        Venta nuevaVenta = ventaService.crearVenta(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizarVenta(@PathVariable Long id, @RequestBody Venta venta) {
        Venta ventaActualizada = ventaService.actualizarVenta(id, venta);
        return ResponseEntity.ok(ventaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }   
}