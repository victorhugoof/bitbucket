package br.com.unisul.verbo.model;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.unisul.verbo.domain.GrupoAcesso;

public class GrupoAcessoModel extends BaseModelImpl<GrupoAcesso, GrupoAcessoModel> {

	@NotNull
	@Length(max = 128)
	String nomGrupo;

	public GrupoAcessoModel() {
		super();
	}

	public GrupoAcessoModel(final GrupoAcesso grupoAcesso) {
		super();
		this.id = grupoAcesso.getId();
		this.nomGrupo = grupoAcesso.getNomGrupo();
	}

	@Override
	public GrupoAcessoModel gerarModel(GrupoAcesso object) {
		return new GrupoAcessoModel(object);
	}
	
	public String getNomGrupo() {
		return nomGrupo;
	}
}

