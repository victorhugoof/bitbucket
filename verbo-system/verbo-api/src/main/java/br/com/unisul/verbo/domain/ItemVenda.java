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
import br.com.unisul.verbo.model.ItemVendaModel;

@Entity
@Table(name = "venditem")
public class ItemVenda extends BaseEntidadeImpl<ItemVenda, ItemVendaModel> {

	@Column(name = "qtd_venda")
	private Integer qtdVenda;

	@Column(name = "num_item")
	private Integer numItem;

	@Column(name = "vlr_unitario", precision = 14, scale = 4)
	private BigDecimal vlrUnitario;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_produto")
	private Produto produto;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_venda")
	private Venda venda;

	public ItemVenda() {
		super();
	}

	public ItemVenda(ItemVendaModel model, Produto produto, Venda venda) {
		this();
		this.qtdVenda = model.getQtdVenda();
		this.vlrUnitario = model.getVlrUnitario();
		this.numItem = model.getNumItem();
		this.produto = produto;
		this.venda = venda;
	}
	
	@Override
	public void atualizar(ItemVendaModel model) {
		this.qtdVenda = model.getQtdVenda();
		this.vlrUnitario = model.getVlrUnitario();
	}
	
	@Override
	public ItemVenda gerarDomain(ItemVendaModel object) {
		throw new NotImplementedException();
	}

	public Integer getQtdVenda() {
		return qtdVenda;
	}

	public Integer getNumItem() {
		return numItem;
	}

	public BigDecimal getVlrUnitario() {
		return vlrUnitario;
	}

	public Produto getProduto() {
		return produto;
	}

	public Venda getVenda() {
		return venda;
	}
}
