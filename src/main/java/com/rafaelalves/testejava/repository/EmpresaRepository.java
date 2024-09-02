package com.rafaelalves.testejava.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelalves.testejava.entities.Empresa;

public interface  EmpresaRepository extends JpaRepository<Empresa, Long>{

	
}
