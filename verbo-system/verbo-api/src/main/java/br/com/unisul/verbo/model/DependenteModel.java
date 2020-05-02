package br.com.unisul.verbo.model;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Dependente;

public class DependenteModel extends BaseModelImpl<Dependente, DependenteModel> {

	@NotNull
	@Length(max = 128)
	String nomDependente;

	@NotNull
	Long codCliente;
	
	public DependenteModel() {
		super();
	}

	public DependenteModel(final Dependente dependente) {
		super();
		this.id = dependente.getId();
		this.nomDependente = dependente.getNomDependente();
		this.codCliente = Objects.nonNull(dependente.getCliente()) ? dependente.getCliente().getId() : null;
	}

	@Override
	public DependenteModel gerarModel(Dependente object) {
		return new DependenteModel(object);
	}

	public String getNomDependente() {
		return nomDependente;
	}

	public Long getCodCliente() {
		return codCliente;
	}
}
