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
import br.com.unisul.verbo.model.CidadeModel;

@Entity
@Table(name = "cidade")
public class Cidade extends BaseEntidadeImpl<Cidade, CidadeModel> {

	@Column(name = "nom_cidade", length = 128)
	private String nomCidade;

	@Valid
	@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
	@JoinColumn(name = "cod_estado")
	private Estado estado;

	@Override
	public Cidade gerarDomain(CidadeModel object) {
		throw new NotImplementedException();
	}

	@Override
	public void atualizar(CidadeModel model) {
		throw new NotImplementedException();
	}
	
	public String getNomCidade() {
		return nomCidade;
	}

	public Estado getEstado() {
		return estado;
	}
}
