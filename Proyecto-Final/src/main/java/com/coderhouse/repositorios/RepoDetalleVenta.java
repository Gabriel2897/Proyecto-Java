package com.coderhouse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderhouse.entidades.DetalleVenta;


public interface RepoDetalleVenta extends JpaRepository<DetalleVenta, Long> {
   
}

