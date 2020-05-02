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
@Table(name = "entradas_itens")
public class ItemEntrada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "cancelado", nullable = false)
	private Boolean cancelado = false;

	@Column(name = "qt_entrada", nullable = false)
	private Integer qt_entrada;

	@Column(name = "vl_unit", nullable = false)
	private Double vl_unit;

	@Column(name = "itemNumero")
	private Integer indice;

	@ManyToOne
	@JoinColumn(name = "id_produto", nullable = false, foreignKey = @ForeignKey(name = "fk_itemEntrada_produto"))
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "id_entrada", nullable = false, foreignKey = @ForeignKey(name = "fk_itemEntrada_entrada"))
	private Entrada id_entrada;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getCancelado() {
		return cancelado;
	}

	public void setCancelado(Boolean cancelado) {
		this.cancelado = cancelado;
	}

	public Integer getQt_entrada() {
		return qt_entrada;
	}

	public void setQt_entrada(Integer qt_entrada) {
		this.qt_entrada = qt_entrada;
	}

	public Double getVl_unit() {
		return vl_unit;
	}

	public void setVl_unit(Double vl_unit) {
		this.vl_unit = vl_unit;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Entrada getId_entrada() {
		return id_entrada;
	}

	public void setId_entrada(Entrada id_entrada) {
		this.id_entrada = id_entrada;
	}

	@Override
	public String toString() {
		return "ItemEntrada [id=" + id + ", cancelado=" + cancelado + ", qt_entrada=" + qt_entrada + ", vl_unit="
				+ vl_unit + ", produto=" + produto + ", id_entrada=" + id_entrada + "]";
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

}
