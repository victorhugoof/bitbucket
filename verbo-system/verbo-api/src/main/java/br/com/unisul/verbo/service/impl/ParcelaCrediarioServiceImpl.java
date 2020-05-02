package br.com.unisul.verbo.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.unisul.verbo.domain.ParcelaCrediario;
import br.com.unisul.verbo.exception.NotImplementedException;
import br.com.unisul.verbo.model.ParcelaCrediarioModel;
import br.com.unisul.verbo.repository.ParcelaCrediarioRepository;
import br.com.unisul.verbo.service.CrediarioService;
import br.com.unisul.verbo.service.ParcelaCrediarioService;

@Transactional
@Service
public class ParcelaCrediarioServiceImpl extends BaseServiceImpl<ParcelaCrediario, ParcelaCrediarioModel> implements ParcelaCrediarioService {

	@Autowired
	ParcelaCrediarioRepository parcelaCrediarioRepository;
	
	@Autowired
	CrediarioService crediarioService;
	
	@Override
	public ParcelaCrediario salvar(ParcelaCrediarioModel model) {
		var crediario = crediarioService.findById(model.getCodCrediario());
		return parcelaCrediarioRepository.save(new ParcelaCrediario(model, crediario));
	}

	@Override
	public Optional<ParcelaCrediario> findByModelOptional(ParcelaCrediarioModel model) {
		// TODO Auto-generated method stub
		throw new NotImplementedException();
	}

	@Override
	public Page<ParcelaCrediarioModel> filter(ParcelaCrediarioModel model, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
