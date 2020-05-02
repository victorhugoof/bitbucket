package br.com.unisul.verbo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.unisul.verbo.domain.BaseEntidadeImpl;
import br.com.unisul.verbo.model.BaseModel;

public interface BaseController<T extends BaseEntidadeImpl<T, M>, M extends BaseModel<T, M>> {
	
	@PutMapping()
	ResponseEntity<M> atualizar(@Valid @RequestBody M model, HttpServletResponse response);
	
	@PostMapping()
	ResponseEntity<M> salvar(@Valid @RequestBody M model, HttpServletResponse response);
	
	@GetMapping()
	ResponseEntity<M> consultar(@Valid @RequestBody M model, HttpServletResponse response);
	
	@GetMapping("/{codigo}")
	ResponseEntity<M> consultar(@PathVariable Long codigo);
	
	@GetMapping("/consultar")
	ResponseEntity<List<M>> consultarTodos();
	
	@DeleteMapping("/{codigo}")
	ResponseEntity<M> remover(@PathVariable Long codigo);
	
	@GetMapping("/json")
	ResponseEntity<String> json() throws JsonProcessingException;
	
	@GetMapping("/page")
	Page<M> filter(M model, Pageable pageable);
}
