package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Entrada;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.EntradaModel;
import br.com.unisul.verbo.repository.EntradaRepository;
import br.com.unisul.verbo.service.EntradaService;

@Transactional
@Service
public class EntradaServiceImpl extends BaseServiceImpl<Entrada, EntradaModel> implements EntradaService {

	@Autowired
	EntradaRepository entradaRepository;

	@Override
	public Optional<Entrada> findByModelOptional(EntradaModel model) {
		if (Objects.isNull(model.getVlrEntrada()) || Objects.isNull(model.getDatEntrada())) {
			throw new BusinessException(getClass());
		}

		return entradaRepository.findFirstByVlrEntradaAndDatEntradaAndCodSituacaoOrderByIdAsc(model.getVlrEntrada(),
				Utils.toZonedDateTime(model.getDatEntrada()), EnumSituacao.ATIVO);
	}

	@Override
	public Page<EntradaModel> filter(EntradaModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
