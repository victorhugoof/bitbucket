package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Dependente;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.DependenteModel;
import br.com.unisul.verbo.repository.DependenteRepository;
import br.com.unisul.verbo.service.ClienteService;
import br.com.unisul.verbo.service.DependenteService;

@Transactional
@Service
public class DependenteServiceImpl extends BaseServiceImpl<Dependente, DependenteModel> implements DependenteService {

	@Autowired
	DependenteRepository dependenteRepository;

	@Autowired
	ClienteService clienteService;

	@Override
	public Dependente salvar(DependenteModel model) {
		var cliente = clienteService.findById(model.getCodCliente());
		return dependenteRepository.save(new Dependente(model, cliente));
	}

	@Override
	public Optional<Dependente> findByModelOptional(DependenteModel model) {
		if (Objects.isNull(model.getNomDependente()) || Objects.isNull(model.getCodCliente())) {
			throw new BusinessException(getClass());
		}

		return dependenteRepository.findFirstByNomDependenteAndClienteIdAndCodSituacaoOrderByIdAsc(
				model.getNomDependente(), model.getCodCliente(), EnumSituacao.ATIVO);
	}

	@Override
	public Page<DependenteModel> filter(DependenteModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
