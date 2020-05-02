package model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "despesas")
public class Despesa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Short id;

	@Column(name = "flg_ativo")
	private Boolean flg_ativo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "valor")
	private Double valor;

	@Column(name = "flg_recorrente")
	private Boolean flg_recorrente;

	@Column(name = "data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Despesa other = (Despesa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getData() {
		return data;
	}

	public String getDescricao() {
		return descricao;
	}

	public Boolean getFlg_ativo() {
		return flg_ativo;
	}

	public Boolean getFlg_recorrente() {
		return flg_recorrente;
	}

	public Short getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setFlg_ativo(Boolean flg_ativo) {
		this.flg_ativo = flg_ativo;
	}

	public void setFlg_recorrente(Boolean flg_recorrente) {
		this.flg_recorrente = flg_recorrente;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

}
