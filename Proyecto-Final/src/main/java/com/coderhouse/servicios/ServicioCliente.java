package com.coderhouse.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.entidades.Cliente;
import com.coderhouse.repositorios.RepoCliente;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@Service
@Tag(name = "ServicioCliente", description = "Servicio para operaciones relacionadas con los clientes")
public class ServicioCliente {

    @Autowired
    private RepoCliente clienteRepository;

    @Operation(summary = "Obtener todos los clientes", description = "Obtiene una lista de todos los clientes registrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente")
    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }

    @Operation(summary = "Obtener un cliente por su ID", description = "Obtiene los detalles de un cliente según su ID")
    @ApiResponse(responseCode = "200", description = "Cliente encontrado")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public Optional<Cliente> obtenerClientePorId(Long id) {
        return clienteRepository.findById(id);
    }

    @Operation(summary = "Guardar un cliente", description = "Guarda un nuevo cliente en la base de datos")
    @ApiResponse(responseCode = "200", description = "Cliente guardado exitosamente")
    public Cliente guardarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Operation(summary = "Actualizar un cliente", description = "Actualiza los datos de un cliente existente en la base de datos")
    @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public Cliente actualizarCliente(Long id, Cliente clienteActualizado) {
        Optional<Cliente> clienteExistenteOptional = clienteRepository.findById(id);
        if (clienteExistenteOptional.isPresent()) {
            Cliente clienteExistente = clienteExistenteOptional.get();
            clienteExistente.setNombre(clienteActualizado.getNombre());
            clienteExistente.setApellido(clienteActualizado.getApellido());
            clienteExistente.setDni(clienteActualizado.getDni());
            clienteExistente.setEmail(clienteActualizado.getEmail());
            clienteExistente.setDireccion(clienteActualizado.getDireccion());
            clienteExistente.setTelefono(clienteActualizado.getTelefono());
            return clienteRepository.save(clienteExistente);
        } else {
            // Manejar el caso en que el cliente no existe
            return null;
        }
    }

    @Operation(summary = "Eliminar un cliente por su ID", description = "Elimina un cliente de la base de datos según su ID")
    @ApiResponse(responseCode = "200", description = "Cliente eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    public void eliminarCliente(Long id) {
        clienteRepository.deleteById(id);
    }
}

