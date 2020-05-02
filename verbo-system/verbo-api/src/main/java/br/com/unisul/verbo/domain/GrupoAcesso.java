package br.com.unisul.verbo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.unisul.verbo.model.GrupoAcessoModel;

@Entity
@Table(name = "grupo_acesso")
public class GrupoAcesso extends BaseEntidadeImpl<GrupoAcesso, GrupoAcessoModel> {

	@Column(name = "nom_grupo")
	private String nomGrupo;

	public GrupoAcesso() {
		super();
	}
	
	public GrupoAcesso(GrupoAcessoModel model) {
		this();
		this.nomGrupo = model.getNomGrupo();
	}
	
	@Override
	public void atualizar(GrupoAcessoModel model) {
		this.nomGrupo = model.getNomGrupo();
	}
	
	@Override
	public GrupoAcesso gerarDomain(GrupoAcessoModel object) {
		return new GrupoAcesso(object);
	}

	public String getNomGrupo() {
		return nomGrupo;
	}
	
}