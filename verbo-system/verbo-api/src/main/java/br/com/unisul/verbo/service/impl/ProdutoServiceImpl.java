package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Produto;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.ProdutoModel;
import br.com.unisul.verbo.repository.ProdutoRepository;
import br.com.unisul.verbo.service.ProdutoService;

@Transactional
@Service
public class ProdutoServiceImpl extends BaseServiceImpl<Produto, ProdutoModel> implements ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public Optional<Produto> findByModelOptional(ProdutoModel model) {
		if (Objects.nonNull(model.getCodBarras())) {
			return produtoRepository.findFirstByCodBarrasAndCodSituacaoOrderByIdAsc(model.getCodBarras(),
					EnumSituacao.ATIVO);
		} else if (Objects.nonNull(model.getNomDescricao())) {
			return produtoRepository.findFirstByNomDescricaoAndCodSituacaoOrderByIdAsc(model.getNomDescricao(),
					EnumSituacao.ATIVO);
		}

		throw new BusinessException(getClass());
	}

	@Override
	public Page<ProdutoModel> filter(ProdutoModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
