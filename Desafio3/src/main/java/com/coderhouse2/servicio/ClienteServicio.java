package com.coderhouse2.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse2.entidades.Cliente;
import com.coderhouse2.repo.ClienteRepository;

@Service
public class ClienteServicio {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente obtenerCliente(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    public String obtenerInfoCliente(Long id) {
        Cliente cliente = obtenerCliente(id);
        if (cliente != null) {
            int edad = cliente.getEdad();
            System.out.println("La edad del cliente " + cliente.getNombre() + " " + cliente.getApellido() + " es: " + edad);
            return "{\"nombre\": \"" + cliente.getNombre() + "\", \"apellido\": \"" + cliente.getApellido() + "\", \"edad\": " + edad + "}";
        } else {
            return null;
        }
    }
}


