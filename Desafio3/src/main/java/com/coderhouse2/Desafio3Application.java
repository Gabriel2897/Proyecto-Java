package com.coderhouse2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.coderhouse2.servicio.ClienteServicio;

@SpringBootApplication
public class Desafio3Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Desafio3Application.class, args);

        ClienteServicio clienteService = context.getBean(ClienteServicio.class);

        Long clienteId = 1L;
        String infoCliente = clienteService.obtenerInfoCliente(clienteId);
        if (infoCliente != null) {
            System.out.println("Información del cliente:");
            System.out.println(infoCliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }
}

