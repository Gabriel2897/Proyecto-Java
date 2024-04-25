package com.coderhouse.entidades;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "cliente")
@Schema(description = "Modelo de Cliente") 
public class Cliente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Schema(description = "Nombre del cliente")
    private String nombre;
    
    @Schema(description = "Apellido del cliente")
    private String apellido;
    
    @Schema(description = "DNI del cliente")
    private String dni;
    
    @Schema(description = "Email del cliente")
    private String email;
    
    @Schema(description = "Dirección del cliente")
    private String direccion;
    
    @Schema(description = "Teléfono del cliente")
    private String telefono;
    
    // Constructor
    public Cliente() {
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    @Override
    public String toString() {
        return String.format("ID: %d\nNombre completo: %s %s\nDNI: %s\nDirección: %s\nTeléfono: %s, Email: %s",
                id, nombre, apellido, dni, direccion, telefono, email);
    }
}
