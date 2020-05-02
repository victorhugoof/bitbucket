package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.GrupoAcesso;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.GrupoAcessoModel;

public interface GrupoAcessoRepository extends BaseRepository<GrupoAcesso, GrupoAcessoModel, Long> {
	Optional<GrupoAcesso> findFirstByNomGrupoAndCodSituacaoOrderByIdAsc(String nome, EnumSituacao situacao);
}
