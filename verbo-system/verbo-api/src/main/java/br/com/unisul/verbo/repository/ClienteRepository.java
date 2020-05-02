package br.com.unisul.verbo.repository;

import java.util.Optional;

import br.com.unisul.verbo.domain.Cliente;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.model.ClienteModel;

public interface ClienteRepository extends BaseRepository<Cliente, ClienteModel, Long>, BaseRepositoryCustom<Cliente, ClienteModel> {
	Optional<Cliente> findFirstByNumCpfAndCodSituacaoOrderByIdAsc(String cpf, EnumSituacao situacao);
}