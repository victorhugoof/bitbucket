package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import br.com.unisul.verbo.domain.ItemEntrada;

public class ItemEntradaModel extends BaseModelImpl<ItemEntrada, ItemEntradaModel> {
	
	@NotNull
	Integer qtdEntrada;

	@NotNull
	BigDecimal vlrUnitario;

	@NotNull
	Integer numItem;

	@NotNull
	Long codProduto;
	
	@NotNull
	Long codEntrada;
	
	public ItemEntradaModel() {
		super();
	}

	public ItemEntradaModel(ItemEntrada item) {
		this();
		this.id = item.getId();
		this.qtdEntrada = item.getQtdEntrada();
		this.vlrUnitario = item.getVlrUnitario();
		this.numItem = item.getNumItem();
		this.codProduto = item.getProduto().getId();
		this.codEntrada = item.getEntrada().getId();
	}
	
	@Override
	public ItemEntradaModel gerarModel(ItemEntrada object) {
		return new ItemEntradaModel(object);
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

	public Long getCodProduto() {
		return codProduto;
	}

	public Long getCodEntrada() {
		return codEntrada;
	}
}
