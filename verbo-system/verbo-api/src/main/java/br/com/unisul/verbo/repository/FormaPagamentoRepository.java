package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.FormaPagamento;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.FormaPagamentoModel;

public interface FormaPagamentoRepository extends BaseRepository<FormaPagamento, FormaPagamentoModel, Long> {
	Optional<FormaPagamento> findFirstByNomFormaAndCodSituacaoOrderByIdAsc(String nome, EnumSituacao situacao);
}
