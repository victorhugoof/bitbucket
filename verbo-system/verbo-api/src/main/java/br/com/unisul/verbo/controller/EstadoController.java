package br.com.unisul.verbo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Estado;
import br.com.unisul.verbo.model.EstadoModel;
import br.com.unisul.verbo.service.EstadoService;

@RestController
@RequestMapping("/estado")
public class EstadoController extends BaseControllerImpl<Estado, EstadoModel> {

	@Autowired
	private EstadoService estadoService;

	@GetMapping("/lista")
	public ResponseEntity<List<EstadoModel>> consultarLista() {
		var lista = estadoService.findAllModel();
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}

}
