package br.com.unisul.verbo.domain;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.ItemEntradaModel;

@Entity
@Table(name = "entritem")
public class ItemEntrada extends BaseEntidadeImpl<ItemEntrada, ItemEntradaModel> {

	@Column(name = "qtd_entrada")
	private Integer qtdEntrada;

	@Column(name = "vlr_unitario", precision = 14, scale = 4)
	private BigDecimal vlrUnitario;

	@Column(name = "num_item")
	private Integer numItem;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_produto")
	private Produto produto;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_entrada")
	private Entrada entrada;

	public ItemEntrada() {
		super();
	}

	public ItemEntrada(ItemEntradaModel model, Produto produto, Entrada entrada) {
		this();
		this.qtdEntrada = model.getQtdEntrada();
		this.vlrUnitario = model.getVlrUnitario();
		this.numItem = model.getNumItem();
		this.produto = produto;
		this.entrada = entrada;
	}

	@Override
	public void atualizar(ItemEntradaModel model) {
		this.qtdEntrada = model.getQtdEntrada();
		this.vlrUnitario = model.getVlrUnitario();
	}
	
	@Override
	public ItemEntrada gerarDomain(ItemEntradaModel object) {
		throw new NotImplementedException();
	}
	
	public Integer getQtdEntrada() {
		return qtdEntrada;
	}

	public BigDecimal getVlrUnitario() {
		return vlrUnitario;
	}

	public Integer getNumItem() {
		return numItem;
	}

	public Produto getProduto() {
		return produto;
	}

	public Entrada getEntrada() {
		return entrada;
	}
}
