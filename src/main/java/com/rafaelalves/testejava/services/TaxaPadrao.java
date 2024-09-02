package com.rafaelalves.testejava.services;


/***
 * Classe contendo taxa padrao a ser implementada
 */
public class TaxaPadrao implements TaxaAdministracao {
    private static final double TAXA_FIXA = 2.0; 

	@Override
	public double calcularTaxa(double valor) {
		double taxaVariavel;
		
		if(valor <= 100.0) {
			taxaVariavel = valor * 0.2;
		}
		else {
			taxaVariavel = valor * 0.15; 
		}
		
		return TAXA_FIXA + taxaVariavel;
		
	}

    
}
