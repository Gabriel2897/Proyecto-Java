package com.coderhouse.servicios;

import com.coderhouse.entidades.ItemVenta;
import com.coderhouse.repositorios.RepoItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ServicioItemVenta {

    @Autowired
    private RepoItem itemVentaRepository;

    public ResponseEntity<Object> crearItemVenta(ItemVenta itemVenta) {
        itemVentaRepository.save(itemVenta);
        return ResponseEntity.ok().body("ItemVenta creado exitosamente");
    }

    public List<ItemVenta> listarTodosLosItemsVenta() {
        return itemVentaRepository.findAll();
    }

    public Optional<ItemVenta> buscarItemVentaPorId(Long id) {
        return itemVentaRepository.findById(id);
    }

    public ResponseEntity<Object> eliminarItemVenta(Long id) {
        Optional<ItemVenta> itemVentaOptional = itemVentaRepository.findById(id);
        if (itemVentaOptional.isPresent()) {
            itemVentaRepository.deleteById(id);
            return ResponseEntity.ok().body("ItemVenta eliminado exitosamente");
        } else {
            return ResponseEntity.badRequest().body("No se encontró el ItemVenta con el ID proporcionado: " + id);
        }
    }

}

