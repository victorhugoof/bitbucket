package br.com.unisul.verbo.repository.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import br.com.unisul.verbo.domain.Cliente;
import br.com.unisul.verbo.model.ClienteModel;
import br.com.unisul.verbo.repository.BaseRepositoryCustom;

public class ClienteRepositoryImpl implements BaseRepositoryCustom<Cliente, ClienteModel> {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private Logger log;
	
	private static final Class<Cliente> ENTITY = Cliente.class;

	@Override
	public Page<ClienteModel> filter(ClienteModel model, Pageable pageable) {

		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(ENTITY);
		var root = criteria.from(ENTITY);
		var query = manager.createQuery(criteria);
		var predicates = criarRestricoes(model, root);
		List<ClienteModel> lista = new ArrayList<>();

		criteria.where(predicates);

		adicionarRestricoesDePaginacao(query, pageable);

		query.getResultList().stream().forEach(domain -> lista.add(model.gerarModel(domain)));

		return new PageImpl<>(lista, pageable, total(model));
	}

	private Predicate[] criarRestricoes(ClienteModel model, Root<Cliente> root) {
		List<Predicate> predicates = new ArrayList<>();
		var cliente = new Cliente(model, null);
		
		for (Field f : cliente.getClass().getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (Objects.nonNull(f.get(this))) {
					f.get(this);
				}
			} catch (IllegalAccessException e) {
				log.error(e.getMessage(), e);
			}
		}

		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Cliente> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}

	private Long total(ClienteModel model) {
		var builder = manager.getCriteriaBuilder();
		var criteria = builder.createQuery(Long.class);
		var root = criteria.from(ENTITY);
		var predicates = criarRestricoes(model, root);

		criteria.where(predicates);
		criteria.select(builder.count(root));

		return manager.createQuery(criteria).getSingleResult();
	}

}
