package br.com.unisul.verbo.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Cidade;

public class CidadeModel extends BaseModelImpl<Cidade, CidadeModel>{
	
	@NotNull
	EstadoModel estado;
	
	@Length(max = 128)
	String nomCidade;

	public CidadeModel() {
    	super();
    }
    
    public CidadeModel(final Cidade cidade) {
    	this();
    	this.id = cidade.getId();
    	this.estado = new EstadoModel(cidade.getEstado());
    	this.nomCidade = cidade.getNomCidade();
	}

    @Override
	public CidadeModel gerarModel(Cidade object) {
		return new CidadeModel(object);
	}
    
	public EstadoModel getEstado() {
		return estado;
	}

	public String getNomCidade() {
		return nomCidade;
	}

}
