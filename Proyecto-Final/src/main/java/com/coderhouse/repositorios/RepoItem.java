package com.coderhouse.repositorios;

import com.coderhouse.entidades.ItemVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoItem extends JpaRepository<ItemVenta, Long> {
 
}
