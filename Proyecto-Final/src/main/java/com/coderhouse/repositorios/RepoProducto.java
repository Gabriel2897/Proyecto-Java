package com.coderhouse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderhouse.entidades.Producto;

@Repository
public interface RepoProducto extends JpaRepository<Producto, Long> {
 
}

