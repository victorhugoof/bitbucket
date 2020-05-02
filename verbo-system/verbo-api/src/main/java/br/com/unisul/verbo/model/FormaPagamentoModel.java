package br.com.unisul.verbo.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.FormaPagamento;

public class FormaPagamentoModel extends BaseModelImpl<FormaPagamento, FormaPagamentoModel> {

	@NotNull
	@Length(max = 128)
	String nomForma;

	public FormaPagamentoModel() {
		super();
	}

	public FormaPagamentoModel(final FormaPagamento formaPagamento) {
		super();
		this.id = formaPagamento.getId();
		this.nomForma = formaPagamento.getNomForma();
	}

	@Override
	public FormaPagamentoModel gerarModel(FormaPagamento object) {
		return new FormaPagamentoModel(object);
	}
	

	public String getNomForma() {
		return nomForma;
	}
}
