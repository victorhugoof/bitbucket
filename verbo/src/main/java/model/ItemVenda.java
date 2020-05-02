package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "itemVenda")
public class ItemVenda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo = true;

	@Column(name = "quantidade")
	private Integer quantidade;

	@Column(name = "itemNumero")
	private Integer indice;

	@Column(name = "vl_unit", nullable = false)
	private Double vl_unit;

	@ManyToOne
	@JoinColumn(name = "codigo_produto", nullable = false, foreignKey = @ForeignKey(name = "fk_itemvenda_produto"))
	private Produto codigo_produto;

	@ManyToOne
	@JoinColumn(name = "codigo_venda", nullable = false, foreignKey = @ForeignKey(name = "fk_itemvenda_venda"))
	private Venda codigo_venda;

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

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Venda getCodigo_venda() {
		return codigo_venda;
	}

	public void setCodigo_venda(Venda codigo_venda) {
		this.codigo_venda = codigo_venda;
	}

	public Produto getCodigo_produto() {
		return codigo_produto;
	}

	public void setCodigo_produto(Produto codigo_produto) {
		this.codigo_produto = codigo_produto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo_produto == null) ? 0 : codigo_produto.hashCode());
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
		ItemVenda other = (ItemVenda) obj;
		if (codigo_produto == null) {
			if (other.codigo_produto != null)
				return false;
		} else if (!codigo_produto.equals(other.codigo_produto))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return id + "";
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public Double getVl_unit() {
		return vl_unit;
	}

	public void setVl_unit(Double vl_unit) {
		this.vl_unit = vl_unit;
	}

}
