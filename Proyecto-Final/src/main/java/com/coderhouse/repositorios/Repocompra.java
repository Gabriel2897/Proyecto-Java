package com.coderhouse.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.coderhouse.entidades.Compra;

@Repository
public interface Repocompra extends JpaRepository<Compra, Long> {
	
}


