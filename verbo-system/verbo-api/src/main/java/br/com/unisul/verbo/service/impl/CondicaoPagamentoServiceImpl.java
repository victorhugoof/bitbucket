package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.CondicaoPagamento;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.CondicaoPagamentoModel;
import br.com.unisul.verbo.repository.CondicaoPagamentoRepository;
import br.com.unisul.verbo.service.CondicaoPagamentoService;

@Transactional
@Service
public class CondicaoPagamentoServiceImpl extends BaseServiceImpl<CondicaoPagamento, CondicaoPagamentoModel>
		implements CondicaoPagamentoService {
	@Autowired
	CondicaoPagamentoRepository condicaoPagamentoRepository;

	@Override
	public Optional<CondicaoPagamento> findByModelOptional(CondicaoPagamentoModel model) {
		if (Objects.isNull(model.getNomCondicao())) {
			throw new BusinessException(getClass());
		}

		return condicaoPagamentoRepository.findFirstByNomCondicaoAndCodSituacaoOrderByIdAsc(model.getNomCondicao(),
				EnumSituacao.ATIVO);
	}

	@Override
	public Page<CondicaoPagamentoModel> filter(CondicaoPagamentoModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
