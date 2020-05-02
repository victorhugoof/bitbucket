package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Despesa;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.DespesaModel;
import br.com.unisul.verbo.repository.DespesaRepository;
import br.com.unisul.verbo.service.DespesaService;

@Transactional
@Service
public class DespesaServiceImpl extends BaseServiceImpl<Despesa, DespesaModel> implements DespesaService {

	@Autowired
	DespesaRepository despesaRepository;

	@Override
	public Optional<Despesa> findByModelOptional(DespesaModel model) {
		if (Objects.isNull(model.getNomDespesa())) {
			throw new BusinessException(getClass());
		}

		return despesaRepository.findFirstByNomDespesaAndCodSituacaoOrderByIdAsc(model.getNomDespesa(),
				EnumSituacao.ATIVO);
	}

	@Override
	public Page<DespesaModel> filter(DespesaModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
