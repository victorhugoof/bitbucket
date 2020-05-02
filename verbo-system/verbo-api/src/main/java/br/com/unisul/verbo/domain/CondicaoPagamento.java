package br.com.unisul.verbo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.model.CondicaoPagamentoModel;

@Entity
@Table(name = "condicao_pagamento")
public class CondicaoPagamento extends BaseEntidadeImpl<CondicaoPagamento, CondicaoPagamentoModel> {

	@Column(name = "nom_condicao")
	private String nomCondicao;

	public CondicaoPagamento() {
		super();
	}
	
	public CondicaoPagamento(CondicaoPagamentoModel model) {
		this();
		this.nomCondicao = model.getNomCondicao();
	}
	
	@Override
	public void atualizar(CondicaoPagamentoModel model) {
		this.nomCondicao = model.getNomCondicao();
	}
	
	@Override
	public CondicaoPagamento gerarDomain(CondicaoPagamentoModel object) {
		return new CondicaoPagamento(object);
	}
    
	public String getNomCondicao() {
		return nomCondicao;
	}
	
}
