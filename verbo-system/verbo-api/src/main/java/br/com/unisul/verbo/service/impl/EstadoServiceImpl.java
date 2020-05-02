package br.com.unisul.verbo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Estado;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.EstadoModel;
import br.com.unisul.verbo.repository.EstadoRepository;
import br.com.unisul.verbo.service.EstadoService;

@Transactional
@Service
public class EstadoServiceImpl extends BaseServiceImpl<Estado, EstadoModel> implements EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	@Override
	public Optional<Estado> findByModelOptional(EstadoModel model) {
		if (Objects.nonNull(model.getNomEstado())) {
			return estadoRepository.findFirstByNomEstadoAndCodSituacaoOrderByIdAsc(model.getNomEstado(),
					EnumSituacao.ATIVO);
		} else if (Objects.nonNull(model.getUfEstado())) {
			return estadoRepository.findFirstByUfEstadoAndCodSituacaoOrderByIdAsc(model.getUfEstado(),
					EnumSituacao.ATIVO);
		}

		throw new BusinessException(getClass());
	}

	@Override
	public List<EstadoModel> findAllModel() {
		var estados = estadoRepository.findAll();

		List<EstadoModel> lista = new ArrayList<>();
		estados.stream().forEach(estado -> lista.add(new EstadoModel(estado)));

		return lista;
	}

	@Override
	public Estado findByUf(String uf) {
		var estado = estadoRepository.findFirstByUfEstadoAndCodSituacaoOrderByIdAsc(uf, EnumSituacao.ATIVO);

		if (!estado.isPresent()) {
			throw new BusinessException(getClass());
		}

		return estado.get();
	}

	@Override
	public Page<EstadoModel> filter(EstadoModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
