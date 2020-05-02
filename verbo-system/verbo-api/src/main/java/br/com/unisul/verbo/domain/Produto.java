package br.com.unisul.verbo.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.model.ProdutoModel;

@Entity
@Table(name = "produto")
public class Produto extends BaseEntidadeImpl<Produto, ProdutoModel> {

	@Column(name = "cod_barras")
	private String codBarras;

	@Column(name = "nom_descricao")
	private String nomDescricao;

	@Column(name = "nom_observacoes")
	private String nomObservacoes;

	@Column(name = "flg_desconto")
	private String flgDesconto;

	@Column(name = "vlr_venda", precision = 14, scale = 4)
	private BigDecimal vlrVenda;

	@Column(name = "qtd_estoque")
	private Integer qtdEstoque;

	@Column(name = "flg_servico")
	private String flgServico;

	public Produto() {
		super();
	}

	public Produto(ProdutoModel model) {
		this();
    	this.codBarras = model.getCodBarras();
    	this.nomDescricao = model.getNomDescricao();
    	this.nomObservacoes = model.getNomObservacoes();
    	this.flgDesconto = model.getFlgDesconto();
    	this.vlrVenda = model.getVlrVenda();
    	this.qtdEstoque = model.getQtdEstoque();
    	this.flgServico = model.getFlgServico();
	}
	
	@Override
	public void atualizar(ProdutoModel model) {
		this.codBarras = model.getCodBarras();
    	this.nomDescricao = model.getNomDescricao();
    	this.nomObservacoes = model.getNomObservacoes();
    	this.flgDesconto = model.getFlgDesconto();
    	this.vlrVenda = model.getVlrVenda();
    	this.flgServico = model.getFlgServico();
	}
	
	@Override
	public Produto gerarDomain(ProdutoModel object) {
		return new Produto(object);
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
