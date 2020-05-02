package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.ParcelaCrediario;
import br.com.unisul.verbo.helper.Utils;

public class ParcelaCrediarioModel extends BaseModelImpl<ParcelaCrediario, ParcelaCrediarioModel> {

	@NotNull
	Integer numParcela;

	@NotNull
	BigDecimal vlrParcela;

	BigDecimal vlrPago;

	@Length(min = 10, max = 10)
	String datPagamento;

	@NotNull
	@Length(min = 10, max = 10)
	String datVencimento;

	@NotNull
	String flgQuitado;

	@NotNull
	Long codCrediario;
	
	public ParcelaCrediarioModel() {
		super();
	}
	
	public ParcelaCrediarioModel(ParcelaCrediario parcela) {
		this();
		this.id = parcela.getId();
		this.numParcela = parcela.getNumParcela();
		this.vlrParcela = parcela.getVlrParcela();
		this.vlrPago = parcela.getVlrPago();
		this.datPagamento = Utils.formatDataBrasil(parcela.getDatPagamento());
		this.datVencimento = Utils.formatDataBrasil(parcela.getDatVencimento());
		this.flgQuitado = parcela.getFlgQuitada();
		this.codCrediario = parcela.getCrediario().getId();
	}
	
	@Override
	public ParcelaCrediarioModel gerarModel(ParcelaCrediario object) {
		return new ParcelaCrediarioModel(object);
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

	public String getDatPagamento() {
		return datPagamento;
	}

	public String getDatVencimento() {
		return datVencimento;
	}

	public String getFlgQuitado() {
		return flgQuitado;
	}

	public Long getCodCrediario() {
		return codCrediario;
	}
}
