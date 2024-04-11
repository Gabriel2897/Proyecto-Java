package com.coderhouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.coderhouse.control.MenuConsola;

@SpringBootApplication
public class ProyectoFinalApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ProyectoFinalApplication.class, args);

        MenuConsola menu = context.getBean(MenuConsola.class);
        menu.mostrarMenuConsola();
    }
}

