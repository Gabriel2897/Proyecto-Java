package com.coderhouse.control;
import com.coderhouse.entidades.Venta;
import com.coderhouse.servicios.ServicioVenta;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/ventas")
public class ControlVenta {

    @Autowired
    private ServicioVenta servicioVenta;

    @Operation(summary = "Ejecutar una venta")
    @PostMapping("/ejecutar")
    public ResponseEntity<Object> ejecutarVenta(@RequestBody Map<String, Object> requestBody) {
        return servicioVenta.ejecutarVenta(requestBody);
    }

    @Operation(summary = "Listar todas las ventas")
    @GetMapping("/listar")
    public List<Venta> listarVentas() {
        return servicioVenta.listarTodasLasVentas();
    }

    @Operation(summary = "Buscar una venta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/buscar/{id}")
    public ResponseEntity<Venta> buscarVentaPorId(@PathVariable Long id) {
        Optional<Venta> venta = servicioVenta.buscarVentaPorId(id);
        return venta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una venta por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Object> eliminarVenta(@PathVariable Long id) {
        return servicioVenta.eliminarVenta(id);
    }
}
