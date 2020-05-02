package br.com.unisul.verbo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Cidade;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.CidadeModel;
import br.com.unisul.verbo.repository.CidadeRepository;
import br.com.unisul.verbo.service.CidadeService;
import br.com.unisul.verbo.service.EstadoService;

@Transactional
@Service
public class CidadeServiceImpl extends BaseServiceImpl<Cidade, CidadeModel> implements CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoService estadoService;

	@Override
	public List<CidadeModel> findAllModelPorEstado(CidadeModel model) {
		var estado = estadoService.findByUf(model.getEstado().getUfEstado());
		var cidades = cidadeRepository.findAllByEstadoIdAndCodSituacaoOrderByIdAsc(estado.getId(), EnumSituacao.ATIVO);

		List<CidadeModel> lista = new ArrayList<>();
		cidades.stream().forEach(cidade -> lista.add(new CidadeModel(cidade)));

		return lista;
	}

	@Override
	public Optional<Cidade> findByModelOptional(CidadeModel model) {
		if ((Objects.isNull(model.getEstado()) && Objects.isNull(model.getEstado().getUfEstado())) || Objects.isNull(model.getNomCidade())) {
			throw new BusinessException(getClass());
		}

		var estado = estadoService.findByUf(model.getEstado().getUfEstado());
		return cidadeRepository.findFirstByNomCidadeAndEstadoIdAndCodSituacaoOrderByIdAsc(model.getNomCidade(),
				estado.getId(), EnumSituacao.ATIVO);
	}

	@Override
	public List<CidadeModel> findAllByEstadoId(Long estadoId) {
		return cidadeRepository.findByEstadoIdAndCodSituacao(estadoId, EnumSituacao.ATIVO);
	}
}
