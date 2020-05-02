package br.com.unisul.verbo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.BaseEntidadeImpl;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.BaseModelImpl;
import br.com.unisul.verbo.repository.BaseRepository;
import br.com.unisul.verbo.service.BaseService;

@Transactional
@Service
public abstract class BaseServiceImpl<T extends BaseEntidadeImpl<T, M>, M extends BaseModelImpl<T, M>>
		implements BaseService<T, M> {

	@Autowired
	private BaseRepository<T, M, Long> repository;
	
	@Autowired
    SimpMessagingTemplate messageingTemplate;
	
	@Override
	public Boolean excluir(Long id) {
		var object = this.findByIdOptional(id);

		if (!object.isPresent()) {
			return false;
		}

		if (object.get() instanceof BaseEntidadeImpl) {
			object.get().excluir();
			repository.save(object.get());
			return true;
		}

		return false;
	}

	@Override
	public T findById(Long id) {
		var object = findByIdOptional(id);

		if (!object.isPresent()) {
			throw new BusinessException(Utils.getClassType(getClass(), 1));
		}

		return object.get();
	}
	
	@Override
	public T findByModel(M model) {
		var object = findByModelOptional(model);

		if (!object.isPresent()) {
			throw new BusinessException(Utils.getClassType(getClass(), 1));
		}

		return object.get();
	}

	@Override
	public Optional<T> findByIdOptional(Long id) {
		return repository.findById(id);
	}

	@Override
	public T salvar(M model) {
		return repository.save(getT().gerarDomain(model));
	}

	@Override
	public T atualizar(M model) {
		if (Objects.isNull(model.getId())) {
			throw new BusinessException(Utils.getClassType(getClass(), 1));
		}

		var object = this.findById(model.getId());

		object.atualizar(model);
		return repository.save(object);
	}

	@Override
	public Page<M> filter(M model, Pageable pageable) {
		throw new NotImplementedException();
	}
	
	@Override
	public List<M> findAll() {
		var lista = new ArrayList<M>();
		repository.findAll().forEach(obj -> lista.add(getM().gerarModel(obj)));
		
		messageingTemplate.convertAndSend("/topic/greetings","Estados ok!");
		return lista;
	}
	
	@SuppressWarnings("unchecked")
	private T getT() {
		return (T) Utils.getObjetoFromClassType(getClass(), 0);
	}
	
	@SuppressWarnings("unchecked")
	private M getM() {
		return (M) Utils.getObjetoFromClassType(getClass(), 1);
	}
	
}
