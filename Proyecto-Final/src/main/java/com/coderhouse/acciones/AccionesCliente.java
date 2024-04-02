package com.coderhouse.acciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.repositorios.Repocliente;

import java.util.List;
import java.util.Optional;

@Service
public class AccionesCliente {

    @Autowired
    private Repocliente clienteRepository;

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    public Cliente guardarCliente(Cliente cliente) {
        // Validaciones
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Optional<Cliente> optionalCliente = clienteRepository.findById(id);
        if (optionalCliente.isPresent()) {
            Cliente cliente = optionalCliente.get();
            cliente.setNombre(clienteActualizado.getNombre());
            cliente.setApellido(clienteActualizado.getApellido());
            cliente.setDni(clienteActualizado.getDni());
            cliente.setDireccion(clienteActualizado.getDireccion());
            cliente.setTelefono(clienteActualizado.getTelefono());
            cliente.setEmail(clienteActualizado.getEmail());
            validarCliente(cliente);
            return clienteRepository.save(cliente);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + id);
        }
    }

    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    private void validarCliente(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().isEmpty()) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio.");
        }
        
        if (cliente.getApellido() == null || cliente.getApellido().isEmpty()) {
            throw new IllegalArgumentException("El apellido del cliente es obligatorio.");
        }
        
        if (cliente.getDni() == null || cliente.getDni().isEmpty()) {
            throw new IllegalArgumentException("El DNI del cliente es obligatorio.");
        }
        
        if (cliente.getDireccion() == null || cliente.getDireccion().isEmpty()) {
            throw new IllegalArgumentException("La dirección del cliente es obligatoria.");
        }
        
        if (cliente.getTelefono() == null || cliente.getTelefono().isEmpty()) {
            throw new IllegalArgumentException("El teléfono del cliente es obligatorio.");
        }
        
        if (cliente.getEmail() == null || cliente.getEmail().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico del cliente es obligatorio.");
        }
        
    }
}

