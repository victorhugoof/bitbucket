package br.com.unisul.verbo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.NotImplementedException;

@NoRepositoryBean
public interface BaseJpaRepository<T, M, I> extends JpaRepository<T, I>{
	Page<T> findByCodSituacao(EnumSituacao situacao, Pageable pageable);

	@Override
	default Page<T> findAll(Pageable pageable) {
		return findByCodSituacao(EnumSituacao.ATIVO, pageable);
	}

	Optional<T> findByIdAndCodSituacao(I id, EnumSituacao situacao);

	@Override
	default Optional<T> findById(I id) {
		return findByIdAndCodSituacao(id, EnumSituacao.ATIVO);
	}

	boolean existsByIdAndCodSituacao(I id, EnumSituacao situacao);

	@Override
	default boolean existsById(I id) {
		return existsByIdAndCodSituacao(id, EnumSituacao.ATIVO);
	}

	Long countByCodSituacao(EnumSituacao situacao);

	@Override
	default long count() {
		return countByCodSituacao(EnumSituacao.ATIVO);
	}

	List<T> findByCodSituacao(EnumSituacao situacao);

	@Override
	default List<T> findAll() {
		return findByCodSituacao(EnumSituacao.ATIVO);
	}

	List<T> findByIdInAndCodSituacao(Iterable<I> ids, EnumSituacao situacao);

	@Override
	default List<T> findAllById(Iterable<I> ids) {
		return findByIdInAndCodSituacao(ids, EnumSituacao.ATIVO);
	}

	T getOneByIdAndCodSituacao(I id, EnumSituacao situacao);
	
	@Override
	default T getOne(I id) {
		return getOneByIdAndCodSituacao(id, EnumSituacao.ATIVO);
	}

	@Override
	default void deleteById(I id) {
		throw new NotImplementedException();
	}

	@Override
	default void delete(T entity) {
		throw new NotImplementedException();
	}

	@Override
	default void deleteAll(Iterable<? extends T> entities) {
		throw new NotImplementedException();
	}

	@Override
	default void deleteAll() {
		throw new NotImplementedException();
	}

	@Override
	default void deleteInBatch(Iterable<T> entities) {
		throw new NotImplementedException();
	}

	@Override
	default void deleteAllInBatch() {
		throw new NotImplementedException();
	}
}
