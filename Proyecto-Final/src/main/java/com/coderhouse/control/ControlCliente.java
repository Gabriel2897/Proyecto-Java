package com.coderhouse.control;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.acciones.ClienteServicio;
import com.coderhouse.entidades.Cliente;

@RestController
@RequestMapping("/clientes")
public class ControlCliente {

    @Autowired
    private ClienteServicio clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.getAllClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.getClienteById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteService.CrearCliente(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        Cliente clienteActualizado = clienteService.ActualizarCliente(id, cliente);
        return ResponseEntity.ok(clienteActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.EliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
