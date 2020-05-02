package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.GrupoAcesso;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.GrupoAcessoModel;
import br.com.unisul.verbo.repository.GrupoAcessoRepository;
import br.com.unisul.verbo.service.GrupoAcessoService;

@Transactional
@Service
public class GrupoAcessoServiceImpl extends BaseServiceImpl<GrupoAcesso, GrupoAcessoModel>
		implements GrupoAcessoService {

	@Autowired
	GrupoAcessoRepository grupoAcessoRepository;

	@Override
	public Optional<GrupoAcesso> findByModelOptional(GrupoAcessoModel model) {

		if (Objects.isNull(model.getNomGrupo())) {
			throw new BusinessException(getClass());
		}

		return grupoAcessoRepository.findFirstByNomGrupoAndCodSituacaoOrderByIdAsc(model.getNomGrupo(),
				EnumSituacao.ATIVO);
	}

	@Override
	public Page<GrupoAcessoModel> filter(GrupoAcessoModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
