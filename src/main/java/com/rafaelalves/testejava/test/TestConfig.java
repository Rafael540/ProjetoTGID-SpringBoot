package com.rafaelalves.testejava.test;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.rafaelalves.testejava.entities.Cliente;
import com.rafaelalves.testejava.entities.Empresa;
import com.rafaelalves.testejava.repository.ClienteRepository;
import com.rafaelalves.testejava.repository.EmpresaRepository;
import com.rafaelalves.testejava.services.TaxaPadrao;
/***
 * Teste de repositório junto ao postman ou h2 console
 */
@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired 
	private EmpresaRepository empresaRepository;

	
	
	@Override
	public void run(String... args) throws Exception {
	
		Cliente cl1= new Cliente(null, "40846585235", "Joao da Silva", 450.0, 600.0);
		Cliente cl2= new Cliente(null, "40888898456", "José Maria", 350.0, 700.0);
		Cliente cl3= new Cliente(null, "33344488856", "Carlos Souza", 800.0, 1000.0);
	

		clienteRepository.saveAll(Arrays.asList(cl1 , cl2, cl3));
	
		
		
		Empresa emp1 = new Empresa(null, "455555554", "MoreiraTapeçaria", 450.0, 5.0);
		Empresa emp2 = new Empresa(null, "455555554", "MoreiraTapeçaria", 450.0, 2.0);
		Empresa emp3 = new Empresa(null, "455555554", "MoreiraTapeçaria", 450.0, 2.0);
		
		empresaRepository.saveAll(Arrays.asList(emp1));

	}
}