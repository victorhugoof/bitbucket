package br.com.unisul.verbo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.model.EstadoModel;

@Entity
@Table(name = "estado")
public class Estado extends BaseEntidadeImpl<Estado, EstadoModel> {

	@Column(name = "uf_estado")
	private String ufEstado;

	@Column(name = "nom_estado")
	private String nomEstado;

	public Estado() {
		super();
	}

	public Estado(EstadoModel model) {
		this();
		this.ufEstado = model.getNomEstado();
		this.nomEstado = model.getUfEstado();
	}

	@Override
	public void atualizar(EstadoModel model) {
		this.nomEstado = model.getUfEstado();
	}
	
	@Override
	public Estado gerarDomain(EstadoModel object) {
		return new Estado(object);
	}
	
	public String getUfEstado() {
		return ufEstado;
	}

	public String getNomEstado() {
		return nomEstado;
	}
}
