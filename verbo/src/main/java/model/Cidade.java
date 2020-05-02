package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "cidades", uniqueConstraints = @UniqueConstraint(columnNames = "codigo_ibge", name = "uk_cidade_id"))
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo_ibge", nullable = false)
	private Integer id;

	@Column(name = "nome", nullable = false)
	private String nome;

	@ManyToOne
	@JoinColumn(name = "codigo_uf", nullable = false, foreignKey = @ForeignKey(name = "fk_cidade_estado"))
	private Estado codigo_uf;

	// GETTERS E SETTERS
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getCodigo_uf() {
		return codigo_uf;
	}

	public void setCodigo_uf(Estado codigo_uf) {
		this.codigo_uf = codigo_uf;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo_uf == null) ? 0 : codigo_uf.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		Cidade other = (Cidade) obj;
		if (codigo_uf == null) {
			if (other.codigo_uf != null)
				return false;
		} else if (!codigo_uf.equals(other.codigo_uf))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

}
