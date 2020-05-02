package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "estados", uniqueConstraints = { @UniqueConstraint(columnNames = "nome", name = "uk_estado_nome"),
		@UniqueConstraint(columnNames = "uf", name = "uk_estado_uf"),
		@UniqueConstraint(columnNames = "codigo_ibge", name = "uk_estado_id") })
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "codigo_ibge", nullable = false)
	private Short id;

	@Column(name = "uf", length = 2, nullable = false)
	private String uf;

	@Column(name = "nome", nullable = false)
	private String nome;

	// GETTERS E SETTERS
	public Short getId() {
		return id;
	}

	public void setId(Short id) {
		this.id = id;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((uf == null) ? 0 : uf.hashCode());
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
		Estado other = (Estado) obj;
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
		if (uf == null) {
			if (other.uf != null)
				return false;
		} else if (!uf.equals(other.uf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return nome;
	}

}
