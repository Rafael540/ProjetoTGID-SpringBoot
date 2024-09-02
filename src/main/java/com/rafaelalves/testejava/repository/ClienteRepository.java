package com.rafaelalves.testejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelalves.testejava.entities.Cliente;

public interface  ClienteRepository extends JpaRepository<Cliente, Long>{

	
}
