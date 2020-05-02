package br.com.unisul.verbo.service;

import java.util.List;

import br.com.unisul.verbo.domain.Estado;
import br.com.unisul.verbo.model.EstadoModel;

public interface EstadoService  extends BaseService<Estado, EstadoModel>{
	Estado findByUf(String uf);
	List<EstadoModel> findAllModel();
}
