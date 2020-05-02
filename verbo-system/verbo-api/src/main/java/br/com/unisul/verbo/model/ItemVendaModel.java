package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.unisul.verbo.domain.ItemVenda;

public class ItemVendaModel extends BaseModelImpl<ItemVenda, ItemVendaModel> {
	
	@NotNull
	Integer qtdVenda;

	@NotNull
	BigDecimal vlrUnitario;

	@NotNull
	Integer numItem;

	@NotNull
	Long codProduto;
	
	@NotNull
	Long codVenda;
	
	public ItemVendaModel() {
		super();
	}
	
	public ItemVendaModel(ItemVenda item) {
		this();
		this.id = item.getId();
		this.qtdVenda = item.getQtdVenda();
		this.vlrUnitario = item.getVlrUnitario();
		this.numItem = item.getNumItem();
		this.codProduto = item.getProduto().getId();
		this.codVenda = item.getVenda().getId();
	}
	
	@Override
	public ItemVendaModel gerarModel(ItemVenda object) {
		return new ItemVendaModel(object);
	}

	public Integer getQtdVenda() {
		return qtdVenda;
	}

	public BigDecimal getVlrUnitario() {
		return vlrUnitario;
	}

	public Integer getNumItem() {
		return numItem;
	}

	public Long getCodProduto() {
		return codProduto;
	}

	public Long getCodVenda() {
		return codVenda;
	}
}
