package com.coderhouse.acciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entidades.Producto;
import com.coderhouse.repositorios.RepoProducto;

import java.util.List;
import java.util.Optional;

@Service
public class AccionesProducto {

	@Autowired
	private RepoProducto productoRepository;

	public List<Producto> listarProductos() {
		return productoRepository.findAll();
	}

	public Optional<Producto> buscarProductoPorId(Long id) {
		return productoRepository.findById(id);
	}

	public Producto guardarProducto(Producto producto) {
		validarProducto(producto);
		return productoRepository.save(producto);
	}

	public Producto actualizarProducto(Long id, Producto productoActualizado) {
		Optional<Producto> optionalProducto = productoRepository.findById(id);
		if (optionalProducto.isPresent()) {
			Producto producto = optionalProducto.get();
			producto.setNombre(productoActualizado.getNombre());
			producto.setPrecio(productoActualizado.getPrecio());
			producto.setCantidad(productoActualizado.getCantidad());
			validarProducto(producto);
			return productoRepository.save(producto);
		} else {
			throw new RuntimeException("Producto no encontrado con id: " + id);
		}
	}

	public void eliminarProducto(Long id) {
		productoRepository.deleteById(id);
	}

	// Método privado para validar un producto antes de guardarlo o actualizarlo
	private void validarProducto(Producto producto) {
		if (producto.getNombre() == null || producto.getNombre().isEmpty()) {
			throw new IllegalArgumentException("El nombre del producto es obligatorio.");
		}

		if (producto.getPrecio() <= 0) {
			throw new IllegalArgumentException("El precio del producto debe ser mayor que cero.");
		}

		if (producto.getCantidad() < 0) {
			throw new IllegalArgumentException("La cantidad del producto no puede ser negativa.");
		}

	}
}
