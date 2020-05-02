package br.com.unisul.verbo.repository;

import java.util.List;
import java.util.Optional;

import br.com.unisul.verbo.domain.Cidade;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.CidadeModel;

public interface CidadeRepository extends BaseRepository<Cidade, CidadeModel, Long> {
	Optional<Cidade> findFirstByNomCidadeAndEstadoIdAndCodSituacaoOrderByIdAsc(String nome, Long estado, EnumSituacao situacao);
	List<Cidade> findAllByEstadoIdAndCodSituacaoOrderByIdAsc(Long estado, EnumSituacao situacao);
	List<CidadeModel> findByEstadoIdAndCodSituacao(Long estadoId, EnumSituacao ativo);
}
