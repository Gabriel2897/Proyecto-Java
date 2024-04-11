package com.coderhouse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coderhouse.entidades.Venta;


public interface RepoVenta extends JpaRepository<Venta, Long> {
   
}

