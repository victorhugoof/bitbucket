package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "vendas")
public class Venda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo = true;

	@Column(name = "dt_emissao")
	@Temporal(TemporalType.DATE)
	private Date dt_emissao = new Date();

	@Column(name = "valor")
	private Double valor;

	@Column(name = "orcamento")
	private Boolean orcamento;

	@ManyToOne
	@JoinColumn(name = "formaPag", nullable = false, foreignKey = @ForeignKey(name = "fk_venda_forma"))
	private FormaPagamento formaPag;

	@ManyToOne
	@JoinColumn(name = "codicaoPag", nullable = false, foreignKey = @ForeignKey(name = "fk_venda_condicao"))
	private CondicaoPagamento condicaoPag;

	@ManyToOne
	@JoinColumn(name = "codigo_cliente", nullable = false, foreignKey = @ForeignKey(name = "fk_venda_cliente"))
	private Cliente codigo_cliente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getFlg_ativo() {
		return flg_ativo;
	}

	public void setFlg_ativo(Boolean flg_ativo) {
		this.flg_ativo = flg_ativo;
	}

	public Date getDt_emissao() {
		return dt_emissao;
	}

	public void setDt_emissao(Date dt_emissao) {
		this.dt_emissao = dt_emissao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Boolean getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Boolean orcamento) {
		this.orcamento = orcamento;
	}

	public FormaPagamento getFormaPag() {
		return formaPag;
	}

	public void setFormaPag(FormaPagamento formaPag) {
		this.formaPag = formaPag;
	}

	public CondicaoPagamento getCondicaoPag() {
		return condicaoPag;
	}

	public void setCondicaoPag(CondicaoPagamento condicaoPag) {
		this.condicaoPag = condicaoPag;
	}

	public Cliente getCodigo_cliente() {
		return codigo_cliente;
	}

	public void setCodigo_cliente(Cliente codigo_cliente) {
		this.codigo_cliente = codigo_cliente;
	}

	// GETTERS E SETTERS



}
