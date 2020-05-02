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
import br.com.unisul.verbo.model.ParcelaCrediarioModel;

@Entity
@Table(name = "credparc")
public class ParcelaCrediario extends BaseEntidadeImpl<ParcelaCrediario, ParcelaCrediarioModel> {

	@Column(name = "num_parcela")
	private Integer numParcela;

	@Column(name = "vlr_parcela", precision = 14, scale = 4)
	private BigDecimal vlrParcela;

	@Column(name = "vlr_pago", precision = 14, scale = 4)
	private BigDecimal vlrPago;

	@Column(name = "dat_pagamento")
	private ZonedDateTime datPagamento;

	@Column(name = "dat_vencimento")
	private ZonedDateTime datVencimento;

	@Column(name = "flg_quitada")
	private String flgQuitada;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_crediario")
	private Crediario crediario;
	
	public ParcelaCrediario() {
		super();
	}

	public ParcelaCrediario(ParcelaCrediarioModel model, Crediario crediario) {
		this();
		this.numParcela = model.getNumParcela();
		this.vlrParcela = model.getVlrParcela();
		this.vlrPago = model.getVlrPago();
		this.datPagamento = Utils.toZonedDateTime(model.getDatPagamento());
		this.datVencimento = Utils.toZonedDateTime(model.getDatVencimento());
		this.flgQuitada = model.getFlgQuitado();
		this.crediario = crediario;
	}
	
	@Override
	public void atualizar(ParcelaCrediarioModel model) {
		this.vlrParcela = model.getVlrParcela();
		this.vlrPago = model.getVlrPago();
		this.datPagamento = Utils.toZonedDateTime(model.getDatPagamento());
		this.datVencimento = Utils.toZonedDateTime(model.getDatVencimento());
		this.flgQuitada = model.getFlgQuitado();
	}
	
	@Override
	public ParcelaCrediario gerarDomain(ParcelaCrediarioModel object) {
		throw new NotImplementedException();
	}

	public Integer getNumParcela() {
		return numParcela;
	}

	public BigDecimal getVlrParcela() {
		return vlrParcela;
	}

	public BigDecimal getVlrPago() {
		return vlrPago;
	}

	public ZonedDateTime getDatPagamento() {
		return datPagamento;
	}

	public ZonedDateTime getDatVencimento() {
		return datVencimento;
	}

	public String getFlgQuitada() {
		return flgQuitada;
	}

	public Crediario getCrediario() {
		return crediario;
	}


}
