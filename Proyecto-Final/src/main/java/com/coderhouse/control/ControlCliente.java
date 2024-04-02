package com.coderhouse.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.coderhouse.acciones.AccionesCliente;
import com.coderhouse.entidades.Cliente;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ControlCliente {

    @Autowired
    private AccionesCliente accionesCliente;

    @GetMapping
    public List<Cliente> listarClientes() {
        return accionesCliente.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id) {
        Optional<Cliente> cliente = accionesCliente.buscarClientePorId(id);
        return cliente.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = accionesCliente.guardarCliente(cliente);
        return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        Cliente cliente = accionesCliente.actualizarCliente(id, clienteActualizado);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        accionesCliente.eliminarCliente(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}



