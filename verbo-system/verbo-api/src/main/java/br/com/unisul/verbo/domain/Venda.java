package br.com.unisul.verbo.domain;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.VendaModel;

@Entity
@Table(name = "venda")
public class Venda extends BaseEntidadeImpl<Venda, VendaModel> {

	@Column(name = "dat_emissao")
	private ZonedDateTime datEmissao;

	@Column(name = "vlr_total", precision = 14, scale = 4)
	private BigDecimal vlrTotal;

	@Column(name = "flg_orcamento")
	private String flgOrcamento;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_forma_pagamento")
	private FormaPagamento formaPagamento;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_condicao_pagamento")
	private CondicaoPagamento condicaoPagamento;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente")
	private Cliente cliente;

	public Venda() {
		super();
	}

	public Venda(VendaModel model, FormaPagamento formaPagamento, CondicaoPagamento condicaoPagamento,
			Cliente cliente) {
		this();
		this.datEmissao = Utils.toZonedDateTime(model.getDatEmissao());
		this.vlrTotal = model.getVlrTotal();
		this.flgOrcamento = model.getFlgOrcamento();
		this.formaPagamento = formaPagamento;
		this.condicaoPagamento = condicaoPagamento;
		this.cliente = cliente;
	}

	public void atualizar(VendaModel model, FormaPagamento formaPagamento, CondicaoPagamento condicaoPagamento,
			Cliente cliente) {
		this.vlrTotal = model.getVlrTotal();
		this.flgOrcamento = model.getFlgOrcamento();
		this.formaPagamento = formaPagamento;
		this.condicaoPagamento = condicaoPagamento;
		this.cliente = cliente;
	}
	
	@Override
	public Venda gerarDomain(VendaModel object) {
		throw new NotImplementedException();
	}

	@Override
	public void atualizar(VendaModel model) {
		throw new NotImplementedException();
	}

	public ZonedDateTime getDatEmissao() {
		return datEmissao;
	}

	public void setDatEmissao(ZonedDateTime datEmissao) {
		this.datEmissao = datEmissao;
	}

	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}

	public void setVlrTotal(BigDecimal vlrTotal) {
		this.vlrTotal = vlrTotal;
	}

	public String getFlgOrcamento() {
		return flgOrcamento;
	}

	public void setFlgOrcamento(String flgOrcamento) {
		this.flgOrcamento = flgOrcamento;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
}
