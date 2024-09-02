package com.rafaelalves.testejava.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rafaelalves.testejava.entities.Empresa;
import com.rafaelalves.testejava.repository.EmpresaRepository;
import com.rafaelalves.testejava.services.exceptions.DataBaseException;
import com.rafaelalves.testejava.services.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;


/***
 * Classe que permite implementar as funcionalidades CRUD na entidade empresa
 */
@Service
public class EmpresaService {

	@Autowired
	public EmpresaRepository repository;

	public List<Empresa> findAll() {
		return repository.findAll();
	}

	public Empresa findById(Long id) {
		Optional<Empresa> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Empresa insert(Empresa obj) {
		return repository.save(obj);
	}

	public void delete(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new  ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataBaseException(e.getMessage());
		}
	}

	public Empresa update(Long id, Empresa obj) {
		try {
			Empresa entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch(EntityNotFoundException e) {

			throw new  ResourceNotFoundException(id);
		}
	}

	private void updateData(Empresa entity, Empresa obj) {
		entity.setRazao(obj.getRazao());
		
	}
}
