package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Crediario;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.CrediarioModel;
import br.com.unisul.verbo.repository.CrediarioRepository;
import br.com.unisul.verbo.service.ClienteService;
import br.com.unisul.verbo.service.CrediarioService;
import br.com.unisul.verbo.service.VendaService;

@Transactional
@Service
public class CrediarioServiceImpl extends BaseServiceImpl<Crediario, CrediarioModel> implements CrediarioService {
	
	@Autowired
	CrediarioRepository crediarioRepository;
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	VendaService vendaService;
	
	@Override
	public Optional<Crediario> findByModelOptional(CrediarioModel model) {
		throw new NotImplementedException();
	}

	@Override
	public Crediario salvar(CrediarioModel model) {
		var cliente = clienteService.findById(model.getCodCliente());
		var venda = Objects.nonNull(model.getCodVenda()) ? vendaService.findById(model.getCodVenda()) : null;
		
		return crediarioRepository.save(new Crediario(model, cliente, venda));
	}

	@Override
	public Crediario atualizar(CrediarioModel model) {
		if (Objects.isNull(model.getId())) {
			throw new BusinessException(getClass());
		}

		var crediario = findById(model.getId());
		var cliente = clienteService.findById(model.getCodCliente());
		var venda = Objects.nonNull(model.getCodVenda()) ? vendaService.findById(model.getCodVenda()) : null;

		crediario.atualizar(model, cliente, venda);
		return crediarioRepository.save(crediario);
	}

	@Override
	public Page<CrediarioModel> filter(CrediarioModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}
}
