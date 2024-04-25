package com.coderhouse.control;
import com.coderhouse.entidades.ItemVenta;
import com.coderhouse.servicios.ServicioItemVenta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items-venta")
public class ControlItemVenta {

    @Autowired
    private ServicioItemVenta servicioItemVenta;

    @Operation(summary = "Crear un nuevo item de venta")
    @PostMapping("/crear")
    public ResponseEntity<Object> crearItemVenta(@RequestBody ItemVenta itemVenta) {
        return servicioItemVenta.crearItemVenta(itemVenta);
    }

    @Operation(summary = "Listar todos los items de venta")
    @GetMapping("/listar")
    public List<ItemVenta> listarItemsVenta() {
        return servicioItemVenta.listarTodosLosItemsVenta();
    }

    @Operation(summary = "Buscar un item de venta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de venta encontrado"),
            @ApiResponse(responseCode = "404", description = "Item de venta no encontrado")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ItemVenta> buscarItemVentaPorId(@PathVariable Long id) {
        Optional<ItemVenta> itemVenta = servicioItemVenta.buscarItemVentaPorId(id);
        return itemVenta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar un item de venta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de venta eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Item de venta no encontrado")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminarItemVenta(@PathVariable Long id) {
        return servicioItemVenta.eliminarItemVenta(id);
    }
}
