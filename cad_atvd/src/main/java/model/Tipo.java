package model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tipo implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	private String tipo_nome; // titulo do tipo de atividade
	
	@Column(length=1)
	private char flg_remove; // se o tipo de atividade vai ser possivel remover
	
	@Column
	private int qnt_carac; // quantidade minima de caracteres para ter na descrição da atividade
	
	@Column(length=1)
	private char flg_sexta; // se pode criar este tipo de atividade na sexta feira
	
	
	// CONSTRUTOR
	public Tipo() {
		
	}
	
	public Tipo(String tipo_nome, char flg_remove, int qnt_carac, char flg_sexta) {
		super();
		this.tipo_nome = tipo_nome;
		this.flg_remove = flg_remove;
		this.qnt_carac = qnt_carac;
		this.flg_sexta = flg_sexta;
	}
	
	// GETTERS E SETTERS
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getQnt_carac() {
		return qnt_carac;
	}
	public void setQnt_carac(int qnt_carac) {
		this.qnt_carac = qnt_carac;
	}
	public char getFlg_remove() {
		return flg_remove;
	}
	public void setFlg_remove(char flg_remove) {
		this.flg_remove = flg_remove;
	}
	public char getFlg_sexta() {
		return flg_sexta;
	}
	public void setFlg_sexta(char flg_sexta) {
		this.flg_sexta = flg_sexta;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
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
		Tipo other = (Tipo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return tipo_nome;
	}
	public String getTipo_nome() {
		return tipo_nome;
	}
	public void setTipo_nome(String tipo_nome) {
		this.tipo_nome = tipo_nome;
	}
	
}
