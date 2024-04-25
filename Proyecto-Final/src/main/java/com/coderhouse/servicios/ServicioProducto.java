package com.coderhouse.servicios;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.coderhouse.entidades.Producto;
import com.coderhouse.repositorios.RepoProducto;

import java.util.List;
import java.util.Optional;

@Service
@Tag(name = "ServicioProducto", description = "Servicio para operaciones relacionadas con los productos")
public class ServicioProducto {

    @Autowired
    private RepoProducto productoRepository;

    @Operation(summary = "Obtener todos los productos", description = "Obtiene una lista de todos los productos registrados")
    @ApiResponse(responseCode = "200", description = "Lista de productos obtenida exitosamente")
    public List<Producto> obtenerTodosProductos() {
        return productoRepository.findAll();
    }

    @Operation(summary = "Obtener un producto por su ID", description = "Obtiene los detalles de un producto según su ID")
    @ApiResponse(responseCode = "200", description = "Producto encontrado")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public Optional<Producto> obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    @Operation(summary = "Guardar un producto", description = "Guarda un nuevo producto en la base de datos")
    @ApiResponse(responseCode = "200", description = "Producto guardado exitosamente")
    public Producto guardarProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    @Operation(summary = "Actualizar un producto", description = "Actualiza los datos de un producto existente en la base de datos")
    @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Optional<Producto> productoExistenteOptional = productoRepository.findById(id);
        if (productoExistenteOptional.isPresent()) {
            Producto productoExistente = productoExistenteOptional.get();
            productoExistente.setProducto(productoActualizado.getProducto());
            productoExistente.setPrecio(productoActualizado.getPrecio());
            productoExistente.setCantidad(productoActualizado.getCantidad());
            return productoRepository.save(productoExistente);
        } else {
            // Manejar el caso en que el producto no existe
            return null;
        }
    }

    @Operation(summary = "Eliminar un producto por su ID", description = "Elimina un producto de la base de datos según su ID")
    @ApiResponse(responseCode = "200", description = "Producto eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }
}

