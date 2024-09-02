package com.rafaelalves.testejava.entities;

import java.io.Serializable;
import java.util.Objects;

import com.rafaelalves.testejava.exception.DomainException;
import com.rafaelalves.testejava.exception.InsufficientBalanceException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "Cliente")
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cpf;
	private String titular;
	private Double saldo;
	private Double limite;
	
	
	public Cliente () {}


	public Cliente(Long id, String cpf, String titular, Double saldo, Double limite) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.titular = titular;
		this.saldo = saldo;
		this.limite = limite;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public String getTitular() {
		return titular;
	}


	public void setTitular(String titular) {
		this.titular = titular;
	}


	public Double getSaldo() {
		return saldo;
	}


	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
	

	public Double getLimite() {
		return limite;
	}


	public void setLimite(Double limite) {
		this.limite = limite;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}
	
	
	/**
	 * 
	 * @param quantia = valor depositado pelo cliente
	 * return valor somatória de saldo em conta com valor a ser depositado
	 */
	public void depositar(double quantia ) {
		saldo += quantia;
	}
	
	
	/***
	 * 
	 * @param quantia = valor depositado pelo cliente
	 * return valor que pode ser sacado pelo cliente, no entanto não é permitido sacar caso o valor não seja o suficiente
	 */
	public void saque(double quantia) {
	    if (quantia > limite) {
	        throw new InsufficientBalanceException("A quantia excede o valor de limite");
	    } 
	    if (saldo < quantia) {
	        throw new InsufficientBalanceException("Saldo não disponível");
	    }
	    
	    saldo -= quantia;
	}

	
	
}
