package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.FormaPagamento;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.FormaPagamentoModel;
import br.com.unisul.verbo.repository.FormaPagamentoRepository;
import br.com.unisul.verbo.service.FormaPagamentoService;

@Transactional
@Service
public class FormaPagamentoServiceImpl extends BaseServiceImpl<FormaPagamento, FormaPagamentoModel>
		implements FormaPagamentoService {

	@Autowired
	FormaPagamentoRepository formaPagamentoRepository;

	@Override
	public Optional<FormaPagamento> findByModelOptional(FormaPagamentoModel model) {

		if (Objects.isNull(model.getNomForma())) {
			throw new BusinessException(getClass());
		}

		return formaPagamentoRepository.findFirstByNomFormaAndCodSituacaoOrderByIdAsc(model.getNomForma(),
				EnumSituacao.ATIVO);
	}

	@Override
	public Page<FormaPagamentoModel> filter(FormaPagamentoModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
