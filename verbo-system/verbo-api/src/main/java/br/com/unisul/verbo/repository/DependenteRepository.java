package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.Dependente;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.DependenteModel;

public interface DependenteRepository extends BaseRepository<Dependente, DependenteModel, Long> {
	Optional<Dependente> findFirstByNomDependenteAndClienteIdAndCodSituacaoOrderByIdAsc(String nome, Long titular, EnumSituacao situacao);
}