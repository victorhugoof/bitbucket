package br.com.unisul.verbo.service;

import java.util.List;

import br.com.unisul.verbo.domain.Cidade;
import br.com.unisul.verbo.model.CidadeModel;

public interface CidadeService extends BaseService<Cidade, CidadeModel> {
	
	List<CidadeModel> findAllModelPorEstado(CidadeModel model);
	List<CidadeModel> findAllByEstadoId(Long estadoId);
}
