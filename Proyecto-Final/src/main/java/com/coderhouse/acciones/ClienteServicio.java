package com.coderhouse.acciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.repositorios.Repocliente;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServicio {

    @Autowired
    private Repocliente clienteRepository;

    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> getClienteById(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente CrearCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente ActualizarCliente(Long id, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        // Actualizar los datos del cliente existente con los datos proporcionados
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellido(cliente.getApellido());
        clienteExistente.setDni(cliente.getDni());
        clienteExistente.setEmail(cliente.getEmail());
        clienteExistente.setDireccion(cliente.getDireccion());
        clienteExistente.setTelefono(cliente.getTelefono());

        return clienteRepository.save(clienteExistente);
    }

    public void EliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}
