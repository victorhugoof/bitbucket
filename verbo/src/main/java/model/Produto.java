package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "produtos", uniqueConstraints = @UniqueConstraint(columnNames = { "cod_barras" }, name = "uk_cod_barras"))
public class Produto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo = true;

	@Column(name = "cod_barras", length = 15)
	private String cod_barras;

	@Column(name = "descricao", length = 80)
	private String descricao;

	@Column(name = "observacoes")
	private String observacoes;

	@Column(name = "permite_desc")
	private Boolean desconto = true;

	@Column(name = "preco")
	private Double preco;

	@Column(name = "qt_estoque")
	private Integer qt_estoque = 0;

	@Column(name = "servico")
	private Boolean servico = false;

	// GETTERS E SETTERS
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

	public String getCod_barras() {
		return cod_barras;
	}

	public void setCod_barras(String cod_barras) {
		this.cod_barras = cod_barras;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Boolean getDesconto() {
		return desconto;
	}

	public void setDesconto(Boolean desconto) {
		this.desconto = desconto;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public Integer getQt_estoque() {
		return qt_estoque;
	}

	public void setQt_estoque(Integer qt_estoque) {
		this.qt_estoque = qt_estoque;
	}

	public Boolean getServico() {
		return servico;
	}

	public void setServico(Boolean servico) {
		this.servico = servico;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cod_barras == null) ? 0 : cod_barras.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (cod_barras == null) {
			if (other.cod_barras != null)
				return false;
		} else if (!cod_barras.equals(other.cod_barras))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
