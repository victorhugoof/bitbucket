package br.com.unisul.verbo.model;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.Estado;

public class EstadoModel extends BaseModelImpl<Estado, EstadoModel>{
	@Length(max = 2)
	String ufEstado;
	
	@Length(max = 128)
	String nomEstado;

	public EstadoModel() {
    	super();
    }
    
    public EstadoModel(final Estado estado) {
    	this();
    	this.id = estado.getId();
    	this.ufEstado = estado.getUfEstado();
    	this.nomEstado = estado.getNomEstado();
	}

    @Override
	public EstadoModel gerarModel(Estado object) {
		return new EstadoModel(object);
	}
    
	public String getUfEstado() {
		return ufEstado;
	}

	public String getNomEstado() {
		return nomEstado;
	}
}
