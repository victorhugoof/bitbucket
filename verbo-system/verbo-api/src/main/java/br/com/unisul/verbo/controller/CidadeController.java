package br.com.unisul.verbo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Cidade;
import br.com.unisul.verbo.model.CidadeModel;
import br.com.unisul.verbo.service.CidadeService;

@RestController
@RequestMapping("/cidade")
public class CidadeController extends BaseControllerImpl<Cidade, CidadeModel> {

	@Autowired
	private CidadeService cidadeService;

	@GetMapping("/lista")
	public ResponseEntity<List<CidadeModel>> consultarLista(@Valid @RequestBody CidadeModel model) {
		var lista = cidadeService.findAllModelPorEstado(model);
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}

}
