package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.Produto;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.ProdutoModel;

public interface ProdutoRepository extends BaseRepository<Produto, ProdutoModel, Long> {
	Optional<Produto> findFirstByCodBarrasAndCodSituacaoOrderByIdAsc(String codBarras, EnumSituacao situacao);
	Optional<Produto> findFirstByNomDescricaoAndCodSituacaoOrderByIdAsc(String descricao, EnumSituacao situacao);
}
