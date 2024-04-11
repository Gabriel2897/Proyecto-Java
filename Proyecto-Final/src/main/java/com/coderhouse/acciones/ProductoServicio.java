package com.coderhouse.acciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entidades.Producto;
import com.coderhouse.repositorios.RepoProducto;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoServicio {

    @Autowired
    private RepoProducto productoRepository;

    public List<Producto> getAllProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto actualizarProducto(Long id, Producto producto) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        // Actualizar los datos del producto existente con los datos proporcionados
        productoExistente.setNombre(producto.getNombre());
        productoExistente.setPrecio(producto.getPrecio());
        productoExistente.setCantidad(producto.getCantidad());

        return productoRepository.save(productoExistente);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}
