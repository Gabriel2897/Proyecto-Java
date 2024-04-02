package com.coderhouse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderhouse.entidades.Cliente;

@Repository
public interface Repocliente extends JpaRepository<Cliente, Long> {
    
}


