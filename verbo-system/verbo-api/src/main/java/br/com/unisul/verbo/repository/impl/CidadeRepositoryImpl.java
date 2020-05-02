package br.com.unisul.verbo.repository.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;

import br.com.unisul.verbo.domain.BaseEntidadeImpl;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.BaseModelImpl;
import br.com.unisul.verbo.repository.CidadeRepositoryCustom;

public class CidadeRepositoryImpl<T extends BaseEntidadeImpl<T,M>, M extends BaseModelImpl<T, M>> implements CidadeRepositoryCustom<T, M> {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<M> filtrarPorPagina(M model) {
		@SuppressWarnings("unchecked")
		var entity = (Class<T>) Utils.getClassType(getClass(), 1);
		var entidade = new PathBuilder<T>(entity, "entidade");

		JPAQuery<T> query = new JPAQuery<T>(manager)
				.from(entidade);
		
		return mp(query.fetch());
		
//		var builder = manager.getCriteriaBuilder();
//		var criteria = builder.createQuery(entity);
//		var root = criteria.from(entity);
//		var query = manager.createQuery(criteria);
//		var predicates = criarRestricoes(model, builder, root);
//		List<M> lista = new ArrayList<>();
//
//		criteria.where(predicates);
//
//		adicionarRestricoesDePaginacao(query, pageable);
//
//		query.getResultList().stream().forEach(domain -> lista.add(getM().gerarModel(domain)));
//
//		return new PageImpl<>(lista, pageable, total(model));
	}

	private List<M> mp(List<T> list) {
        return list.stream().map(domain -> getM().gerarModel(domain)).collect(Collectors.toList());
    }
	
//	private Predicate[] criarRestricoes(M model, CriteriaBuilder builder, Root<T> root) {
//		List<Predicate> predicates = new ArrayList<>();
//
//		for (Field f : getClass().getFields()) {
//			f.setAccessible(true);
//			try {
//				if (f.get(this) == null) {
//
//				}
//			} catch (IllegalAccessException e) { // shouldn't happen because I used setAccessible
//			}
//		}
//
//		return predicates.toArray(new Predicate[predicates.size()]);
//	}
//
//	private void adicionarRestricoesDePaginacao(TypedQuery<T> query, Pageable pageable) {
//		int paginaAtual = pageable.getPageNumber();
//		int totalRegistrosPorPagina = pageable.getPageSize();
//		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
//
//		query.setFirstResult(primeiroRegistroDaPagina);
//		query.setMaxResults(totalRegistrosPorPagina);
//	}
//
//	private Long total(M model) {
//		var builder = manager.getCriteriaBuilder();
//		var criteria = builder.createQuery(Long.class);
//		var root = criteria.from(entity);
//		var predicates = criarRestricoes(model, builder, root);
//
//		criteria.where(predicates);
//		criteria.select(builder.count(root));
//
//		return manager.createQuery(criteria).getSingleResult();
//	}

	@SuppressWarnings("unchecked")
	private M getM() {
		return (M) Utils.getObjetoFromClassType(getClass(), 1);
	}
}
