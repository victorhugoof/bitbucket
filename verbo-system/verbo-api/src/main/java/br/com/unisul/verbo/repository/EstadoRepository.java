package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.Estado;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.EstadoModel;

public interface EstadoRepository extends BaseRepository<Estado, EstadoModel, Long> {
	Optional<Estado> findFirstByUfEstadoAndCodSituacaoOrderByIdAsc(String uf, EnumSituacao situacao);
	Optional<Estado> findFirstByNomEstadoAndCodSituacaoOrderByIdAsc(String nome, EnumSituacao situacao);
}
