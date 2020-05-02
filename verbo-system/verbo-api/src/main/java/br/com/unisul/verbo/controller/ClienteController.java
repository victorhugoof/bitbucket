package br.com.unisul.verbo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.unisul.verbo.domain.Cliente;
import br.com.unisul.verbo.model.CidadeModel;
import br.com.unisul.verbo.model.ClienteModel;
import br.com.unisul.verbo.model.EstadoModel;
import br.com.unisul.verbo.service.CidadeService;
import br.com.unisul.verbo.service.ClienteService;
import br.com.unisul.verbo.service.EstadoService;

@RestController
@RequestMapping("/cliente")
public class ClienteController extends BaseControllerImpl<Cliente, ClienteModel> {
	
	@Autowired
	ClienteService clienteService;
	
	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private CidadeService cidadeService;
	
	@PostMapping("/consultarTodos")
	public ResponseEntity<List<ClienteModel>> consultar(HttpServletResponse response) {
		return ResponseEntity.ok(clienteService.findAll());
	}
	
	
	@GetMapping("/cidades/{estadoId}")
	public ResponseEntity<List<CidadeModel>> consultarCidades(@PathVariable Long estadoId) {
		var lista = cidadeService.findAllByEstadoId(estadoId);
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
	@GetMapping("/estados")
	public ResponseEntity<List<EstadoModel>> consultarEstado() {
		var lista = estadoService.findAll();
		return !lista.isEmpty() ? ResponseEntity.ok(lista) : ResponseEntity.noContent().build();
	}
	
}
