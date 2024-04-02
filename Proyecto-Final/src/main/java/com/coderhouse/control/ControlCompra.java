package com.coderhouse.control;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.acciones.AccionesCompra;
import com.coderhouse.entidades.Compra;

@RestController
@RequestMapping("/compra")
public class ControlCompra {

    @Autowired
    private AccionesCompra accionesCompra;
    
    @GetMapping("/listar")
    public ResponseEntity<List<Compra>> listarCompras() {
        List<Compra> compras = accionesCompra.listarCompras();
        return ResponseEntity.ok(compras);
    }

    @PostMapping("/realizar")
    public ResponseEntity<Compra> realizarCompra(@RequestBody Compra compra) {
        Compra nuevaCompra = accionesCompra.realizarCompra(compra);
        return new ResponseEntity<>(nuevaCompra, HttpStatus.CREATED);
    }

    @PostMapping("/cancelar/{id}")
    public ResponseEntity<String> cancelarCompra(@PathVariable Long id, @RequestParam String motivo) {
        accionesCompra.cancelarCompra(id, motivo);
        return ResponseEntity.ok("Compra cancelada con éxito.");
    }

    @GetMapping("/total/{id}")
    public ResponseEntity<Double> obtenerTotalCompra(@PathVariable Long id) {
        double total = accionesCompra.obtenerTotalCompra(id);
        return ResponseEntity.ok(total);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<String> detalleCompra(@PathVariable Long id) {
        String detalle = accionesCompra.detalleCompra(id);
        return ResponseEntity.ok(detalle);
    }
    
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarCompra(@PathVariable Long id) {
        accionesCompra.eliminarCompra(id);
        return ResponseEntity.ok("Compra eliminada con éxito.");
    }
}



