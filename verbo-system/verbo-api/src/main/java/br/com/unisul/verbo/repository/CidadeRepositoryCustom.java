package br.com.unisul.verbo.repository;

import java.util.List;

public interface CidadeRepositoryCustom<T, M> {
	List<M> filtrarPorPagina(M model);
}
