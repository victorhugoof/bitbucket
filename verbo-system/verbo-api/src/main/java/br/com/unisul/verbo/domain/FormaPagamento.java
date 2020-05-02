package br.com.unisul.verbo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.model.FormaPagamentoModel;

@Entity
@Table(name = "forma_pagamento")
public class FormaPagamento extends BaseEntidadeImpl<FormaPagamento, FormaPagamentoModel> {

	@Column(name = "nom_forma")
	private String nomForma;

	public FormaPagamento() {
		super();
	}
	
	public FormaPagamento(FormaPagamentoModel model) {
		this();
		this.nomForma = model.getNomForma();
	}
	
	@Override
	public void atualizar(FormaPagamentoModel model) {
		this.nomForma = model.getNomForma();
	}
	
	@Override
	public FormaPagamento gerarDomain(FormaPagamentoModel object) {
		return new FormaPagamento(object);
	}

	public String getNomForma() {
		return nomForma;
	}
	
}
