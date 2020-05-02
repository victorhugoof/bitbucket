package br.com.unisul.verbo.service.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.Cliente;
import br.com.unisul.verbo.enuns.EnumSituacao;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.model.ClienteModel;
import br.com.unisul.verbo.repository.ClienteRepository;
import br.com.unisul.verbo.service.CidadeService;
import br.com.unisul.verbo.service.ClienteService;

@Transactional
@Service
public class ClienteServiceImpl extends BaseServiceImpl<Cliente, ClienteModel> implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private CidadeService cidadeService;

	@Override
	public Optional<Cliente> findByModelOptional(ClienteModel model) {
		if (Objects.isNull(model.getNumCpf())) {
			throw new BusinessException(getClass());
		}

		return clienteRepository.findFirstByNumCpfAndCodSituacaoOrderByIdAsc(model.getNumCpf(), EnumSituacao.ATIVO);
	}

	@Override
	public Cliente salvar(ClienteModel model) {
		var cidade = Objects.nonNull(model.getCidade()) ? cidadeService.findById(model.getCidade().getId()) : null;
		return clienteRepository.save(new Cliente(model, cidade));
	}

	@Override
	public Cliente atualizar(ClienteModel model) {
		if (Objects.isNull(model.getId())) {
			throw new BusinessException(getClass());
		}

		var cliente = findById(model.getId());
		var cidade = Objects.nonNull(model.getCidade()) ? cidadeService.findById(model.getCidade().getId()) : null;

		cliente.atualizar(model, cidade);
		return clienteRepository.save(cliente);
	}

	@Override
	public Page<ClienteModel> filter(ClienteModel model, Pageable pageable) {
		return clienteRepository.filter(model, pageable);
	}
	
}
