package br.com.unisul.verbo.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.DependenteModel;

@Entity
@Table(name = "dependente")
public class Dependente extends BaseEntidadeImpl<Dependente, DependenteModel> {

	@Column(name = "nom_dependente")
	private String nomDependente;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_cliente")
	private Cliente cliente;

	public Dependente() {
		super();
	}

	public Dependente(DependenteModel model, Cliente cliente) {
		this();
		this.nomDependente = model.getNomDependente();
		this.cliente = cliente;
	}

	@Override
	public void atualizar(DependenteModel model) {
		this.nomDependente = model.getNomDependente();
	}
	
	@Override
	public Dependente gerarDomain(DependenteModel object) {
		throw new NotImplementedException();
	}
	
	public String getNomDependente() {
		return nomDependente;
	}

	public Cliente getCliente() {
		return cliente;
	}
}
