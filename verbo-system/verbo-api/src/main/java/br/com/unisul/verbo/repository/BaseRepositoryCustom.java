package br.com.unisul.verbo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BaseRepositoryCustom<T, M> {
	Page<M> filter(M model, Pageable pageable);
}
