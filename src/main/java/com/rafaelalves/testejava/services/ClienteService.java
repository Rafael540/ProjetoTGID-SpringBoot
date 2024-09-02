package com.rafaelalves.testejava.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.rafaelalves.testejava.entities.Cliente;
import com.rafaelalves.testejava.repository.ClienteRepository;
import com.rafaelalves.testejava.servicos.exceptions.DataBaseException;
import com.rafaelalves.testejava.servicos.exceptions.ResourceNotFoundException;

import jakarta.persistence.EntityNotFoundException;

/***
 *  Classe que permite implementar as funcionalidades CRUD na entidade clientes
 */

@Service
public class ClienteService {

	@Autowired
	public ClienteRepository repository;

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Cliente findById(Long id) {
		Optional<Cliente> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Cliente insert(Cliente obj) {
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

	public Cliente update(Long id, Cliente obj) {
		try {
			Cliente entity = repository.getReferenceById(id);
			updateData(entity, obj);
			return repository.save(entity);
		} catch(EntityNotFoundException e) {

			throw new  ResourceNotFoundException(id);
		}
	}

	private void updateData(Cliente entity, Cliente obj) {
		entity.setTitular(obj.getTitular());
		
	}
}
