package com.coderhouse.control;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.acciones.AccionesProducto;
import com.coderhouse.entidades.Producto;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ControlProducto {

    @Autowired
    private AccionesProducto accionesProducto;

    @GetMapping
    public List<Producto> listarProductos() {
        return accionesProducto.listarProductos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = accionesProducto.buscarProductoPorId(id);
        return producto.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Producto> guardarProducto(@RequestBody Producto producto) {
        Producto nuevoProducto = accionesProducto.guardarProducto(producto);
        return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto productoActualizado) {
        Producto producto = accionesProducto.actualizarProducto(id, productoActualizado);
        return new ResponseEntity<>(producto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        accionesProducto.eliminarProducto(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

