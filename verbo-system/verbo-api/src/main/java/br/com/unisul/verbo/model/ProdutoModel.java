package br.com.unisul.verbo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Produto;

public class ProdutoModel extends BaseModelImpl<Produto, ProdutoModel> {

	@Length(min = 13, max = 15)
	String codBarras;

	@NotNull
	@Length(max = 128)
	String nomDescricao;
	
	String nomObservacoes;
	
	@NotNull
	@Length(max = 1)
	String flgDesconto;
	
	@NotNull
	BigDecimal vlrVenda;

	@NotNull
	Integer qtdEstoque;

	@NotNull
	@Length(max = 1)
	String flgServico;
	
	public ProdutoModel() {
    	super();
    }

	public ProdutoModel(final Produto produto) {
    	this();
    	this.id = produto.getId();
    	this.codBarras = produto.getCodBarras();
    	this.nomDescricao = produto.getNomDescricao();
    	this.nomObservacoes = produto.getNomObservacoes();
    	this.flgDesconto = produto.getFlgDesconto();
    	this.vlrVenda = produto.getVlrVenda();
    	this.qtdEstoque = produto.getQtdEstoque();
    	this.flgServico = produto.getFlgServico();
	}

	@Override
	public ProdutoModel gerarModel(Produto object) {
		return new ProdutoModel(object);
	}

	public String getCodBarras() {
		return codBarras;
	}

	public String getNomDescricao() {
		return nomDescricao;
	}

	public String getNomObservacoes() {
		return nomObservacoes;
	}

	public String getFlgDesconto() {
		return flgDesconto;
	}

	public BigDecimal getVlrVenda() {
		return vlrVenda;
	}

	public Integer getQtdEstoque() {
		return qtdEstoque;
	}

	public String getFlgServico() {
		return flgServico;
	}
}
