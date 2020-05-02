package br.com.unisul.verbo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.unisul.verbo.model.ClienteModel;

public interface ClienteRepositoryCustom {
	Page<ClienteModel> filtrarPagina(ClienteModel model, Pageable pageable);
}
