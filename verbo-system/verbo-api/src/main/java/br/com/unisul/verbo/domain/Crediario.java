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
import br.com.unisul.verbo.model.CrediarioModel;

@Entity
@Table(name = "crediario")
public class Crediario extends BaseEntidadeImpl<Crediario, CrediarioModel> {

	@Column(name = "dat_emissao")
	private ZonedDateTime datEmissao;

	@Column(name = "vlr_total", nullable = false)
	private BigDecimal vlrTotal;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente")
	private Cliente cliente;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_venda")
	private Venda venda;

	public Crediario() {
		super();
	}

	public Crediario(CrediarioModel model, Cliente cliente, Venda venda) {
		this();
		this.datEmissao = Utils.toZonedDateTime(model.getDatEmissao());
		this.vlrTotal = model.getVlrTotal();
		this.cliente = cliente;
		this.venda = venda;
	}

	public void atualizar(CrediarioModel model, Cliente cliente, Venda venda) {
		this.vlrTotal = model.getVlrTotal();
		this.cliente = cliente;
		this.venda = venda;
	}

	@Override
	public Crediario gerarDomain(CrediarioModel object) {
		throw new NotImplementedException();
	}

	@Override
	public void atualizar(CrediarioModel model) {
		throw new NotImplementedException();
	}

	public ZonedDateTime getDatEmissao() {
		return datEmissao;
	}

	public BigDecimal getVlrTotal() {
		return vlrTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public Venda getVenda() {
		return venda;
	}

}
