package br.com.unisul.verbo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.unisul.verbo.model.BaseModel;

public interface BaseService<T, M extends BaseModel<T, M>> {
	T findById(Long id);
	T findByModel(M model);
	Optional<T> findByIdOptional(Long id);
	Optional<T> findByModelOptional(M model);
	T salvar(M model);
	T atualizar(M model);
	Boolean excluir(Long id);
	Page<M> filter(M model, Pageable pageable);
	List<M> findAll();
}
