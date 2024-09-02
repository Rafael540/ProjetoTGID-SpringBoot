package com.rafaelalves.testejava.entities;

import java.util.Objects;

import com.rafaelalves.testejava.exception.InsufficientBalanceException;
import com.rafaelalves.testejava.services.TaxaAdministracao;
import com.rafaelalves.testejava.servicos.exceptions.DataBaseException;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Empresa")
public class Empresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String cnpj;
	private String razao;
	private Double saldo;
	private Double taxaAdministração;

	public Empresa() {

	}

	public Empresa(Long id, String cnpj, String razao, Double saldo, Double taxaAdministração) {
		this.id = id;
		this.cnpj = cnpj;
		this.razao = razao;
		this.saldo = saldo;
		this.taxaAdministração = taxaAdministração;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCpnj() {
		return cnpj;
	}

	public void setCpnj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public Double getSaldo() {
		return saldo;
	}

	public Double getTaxaAdministração() {
		return taxaAdministração;
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
		Empresa other = (Empresa) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Método utilizado para calcular transação de acordo com os parâmetros
	 * definidos na entidade cliente. Além disso, implementa a taxa de administração
	 * sobre o valor transacionado.
	 * 
	 * @param valor             = Valor da transação
	 * @param deposito          = Indica se a operação é um depósito
	 * @param cliente           = Cliente associado à transação
	 * @param taxaAdministracao = Taxa básica return valor de transacao
	 */

	public void transacao(double valor, boolean deposito, Cliente cliente, TaxaAdministracao taxaAdministracao) {
		double taxaValor = taxaAdministracao.calcularTaxa(valor);
		if (deposito) {
			if (cliente != null) {
				cliente.depositar(valor);
				this.saldo += valor - taxaValor;
			} else {
				throw new DataBaseException("Cliente inválido para depósito.");
			}
		} else {
			if (valor + taxaValor <= saldo) {
				saldo -= (valor + taxaValor);
			} else {
				throw new InsufficientBalanceException("Saldo insuficiente!");
			}
		}

	}
}