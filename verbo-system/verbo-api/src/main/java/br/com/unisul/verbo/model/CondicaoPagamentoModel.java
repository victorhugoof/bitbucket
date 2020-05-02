package br.com.unisul.verbo.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.CondicaoPagamento;

public class CondicaoPagamentoModel extends BaseModelImpl<CondicaoPagamento, CondicaoPagamentoModel> {

	@NotNull
	@Length(max = 128)
	String nomCondicao;

	public CondicaoPagamentoModel() {
		super();
	}

	public CondicaoPagamentoModel(final CondicaoPagamento condicaoPagamento) {
		super();
		this.id = condicaoPagamento.getId();
		this.nomCondicao = condicaoPagamento.getNomCondicao();
	}

	@Override
	public CondicaoPagamentoModel gerarModel(CondicaoPagamento object) {
		return new CondicaoPagamentoModel(object);
	}

	public String getNomCondicao() {
		return nomCondicao;
	}
}
