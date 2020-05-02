package br.com.unisul.verbo.repository;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import br.com.unisul.verbo.domain.Entrada;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.EntradaModel;

public interface EntradaRepository extends BaseRepository<Entrada, EntradaModel, Long> {
	Optional<Entrada> findFirstByVlrEntradaAndDatEntradaAndCodSituacaoOrderByIdAsc(BigDecimal valor, ZonedDateTime datEntrada, EnumSituacao situacao);
}
