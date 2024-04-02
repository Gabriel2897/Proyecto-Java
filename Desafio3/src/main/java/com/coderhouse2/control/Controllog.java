package com.coderhouse2.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse2.servicio.ClienteServicio;

@RestController
@RequestMapping("/clientes")
public class Controllog {

    @Autowired
    private ClienteServicio clienteService;

    @GetMapping("/{id}")
    public String obtenerInfoCliente(@PathVariable Long id) {
        return clienteService.obtenerInfoCliente(id);
    }
}



