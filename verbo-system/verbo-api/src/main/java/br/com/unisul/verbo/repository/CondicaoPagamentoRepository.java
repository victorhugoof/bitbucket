package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.CondicaoPagamento;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.CondicaoPagamentoModel;

public interface CondicaoPagamentoRepository extends BaseRepository<CondicaoPagamento, CondicaoPagamentoModel, Long> {
	Optional<CondicaoPagamento> findFirstByNomCondicaoAndCodSituacaoOrderByIdAsc(String nome, EnumSituacao situacao);
}
