package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Venda;
import br.com.unisul.verbo.helper.Utils;

public class VendaModel extends BaseModelImpl<Venda, VendaModel> {

	@NotNull
	@Length(min = 10, max = 10)
	String datEmissao;

	@NotNull
	BigDecimal vlrTotal;

	@NotNull
	@Length(min = 1, max = 1)
	String flgOrcamento;

	@NotNull
	Long codFormaPagamento;

	@NotNull
	Long codCondicaoPagamento;

	@NotNull
	Long codCliente;
	
	public VendaModel() {
		super();
	}

	public VendaModel(Venda venda) {
		this();
		this.id = venda.getId();
		this.datEmissao = Utils.formatDataBrasil(venda.getDatEmissao());
		this.vlrTotal = venda.getVlrTotal();
		this.flgOrcamento = venda.getFlgOrcamento();
		this.codFormaPagamento = venda.getFormaPagamento().getId();
		this.codCondicaoPagamento = venda.getCondicaoPagamento().getId();
		this.codCliente = venda.getCliente().getId();
	}
	
	@Override
	public VendaModel gerarModel(Venda object) {
		return new VendaModel(object);
	}

	public String getDatEmissao() {
		return datEmissao;
	}

	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}

	public String getFlgOrcamento() {
		return flgOrcamento;
	}

	public Long getCodFormaPagamento() {
		return codFormaPagamento;
	}

	public Long getCodCondicaoPagamento() {
		return codCondicaoPagamento;
	}

	public Long getCodCliente() {
		return codCliente;
	}
}
