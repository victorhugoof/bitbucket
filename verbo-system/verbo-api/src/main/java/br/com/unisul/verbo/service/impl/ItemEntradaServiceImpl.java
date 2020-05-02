package br.com.unisul.verbo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.ItemEntrada;
import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.ItemEntradaModel;
import br.com.unisul.verbo.repository.ItemEntradaRepository;
import br.com.unisul.verbo.service.EntradaService;
import br.com.unisul.verbo.service.ItemEntradaService;
import br.com.unisul.verbo.service.ProdutoService;

@Transactional
@Service
public class ItemEntradaServiceImpl extends BaseServiceImpl<ItemEntrada, ItemEntradaModel> implements ItemEntradaService {

	@Autowired
	ItemEntradaRepository itemEntradaRepository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	EntradaService entradaService;
	
	@Override
	public ItemEntrada salvar(ItemEntradaModel model) {
		var produto = produtoService.findById(model.getCodProduto());
		var entrada = entradaService.findById(model.getCodEntrada());
		return itemEntradaRepository.save(new ItemEntrada(model, produto, entrada));
	}

	@Override
	public Optional<ItemEntrada> findByModelOptional(ItemEntradaModel model) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public Page<ItemEntradaModel> filter(ItemEntradaModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
