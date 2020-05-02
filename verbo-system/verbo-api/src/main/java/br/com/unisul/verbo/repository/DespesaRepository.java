package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.Despesa;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.DespesaModel;

public interface DespesaRepository extends BaseRepository<Despesa, DespesaModel, Long> {
	Optional<Despesa> findFirstByNomDespesaAndCodSituacaoOrderByIdAsc(String nome, EnumSituacao situacao);
}