package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Venda;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.VendaModel;
import br.com.unisul.verbo.repository.VendaRepository;
import br.com.unisul.verbo.service.ClienteService;
import br.com.unisul.verbo.service.CondicaoPagamentoService;
import br.com.unisul.verbo.service.FormaPagamentoService;
import br.com.unisul.verbo.service.VendaService;

@Transactional
@Service
public class VendaServiceImpl extends BaseServiceImpl<Venda, VendaModel> implements VendaService {
	
	@Autowired
	VendaRepository vendaRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	FormaPagamentoService formaPagamentoService;
	
	@Autowired
	CondicaoPagamentoService condicaoPagamentoService;

	@Override
	public Optional<Venda> findByModelOptional(VendaModel model) {
		throw new NotImplementedException();
	}

	@Override
	public Venda salvar(VendaModel model) {
		var cliente = clienteService.findById(model.getCodCliente());
		var formaPagamento = formaPagamentoService.findById(model.getCodFormaPagamento());
		var condicaoPagamento = condicaoPagamentoService.findById(model.getCodCondicaoPagamento());
		return vendaRepository.save(new Venda(model, formaPagamento, condicaoPagamento, cliente));
	}

	@Override
	public Venda atualizar(VendaModel model) {
		if (Objects.isNull(model.getId())) {
			throw new BusinessException(getClass());
		}

		var venda = findById(model.getId());
		var cliente = clienteService.findById(model.getCodCliente());
		var formaPagamento = formaPagamentoService.findById(model.getCodFormaPagamento());
		var condicaoPagamento = condicaoPagamentoService.findById(model.getCodCondicaoPagamento());

		venda.atualizar(model, formaPagamento, condicaoPagamento, cliente);
		return vendaRepository.save(venda);
	}

	@Override
	public Page<VendaModel> filter(VendaModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
