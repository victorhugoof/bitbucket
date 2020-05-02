package br.com.unisul.verbo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.ItemVenda;
import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.ItemVendaModel;
import br.com.unisul.verbo.repository.ItemVendaRepository;
import br.com.unisul.verbo.service.VendaService;
import br.com.unisul.verbo.service.ItemVendaService;
import br.com.unisul.verbo.service.ProdutoService;

@Transactional
@Service
public class ItemVendaServiceImpl extends BaseServiceImpl<ItemVenda, ItemVendaModel> implements ItemVendaService {

	@Autowired
	ItemVendaRepository itemVendaRepository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	VendaService vendaService;
	
	@Override
	public ItemVenda salvar(ItemVendaModel model) {
		var produto = produtoService.findById(model.getCodProduto());
		var venda = vendaService.findById(model.getCodVenda());
		return itemVendaRepository.save(new ItemVenda(model, produto, venda));
	}

	@Override
	public Optional<ItemVenda> findByModelOptional(ItemVendaModel model) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public Page<ItemVendaModel> filter(ItemVendaModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
