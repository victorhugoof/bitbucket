package br.com.unisul.verbo.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;

import br.com.unisul.verbo.domain.BaseEntidadeImpl;
import br.com.unisul.verbo.event.RecursoAtualizadoEvent;
import br.com.unisul.verbo.event.RecursoCriadoEvent;
import br.com.unisul.verbo.helper.Utils;
import br.com.unisul.verbo.model.BaseModelImpl;
import br.com.unisul.verbo.service.BaseService;

public abstract class BaseControllerImpl<T extends BaseEntidadeImpl<T, M>, M extends BaseModelImpl<T, M>>
		implements BaseController<T, M> {

	@Autowired
	protected ApplicationEventPublisher publisher;

	@Autowired
	private BaseService<T, M> service;

	@Override
	@DeleteMapping("/{codigo}")
	public ResponseEntity<M> remover(@PathVariable Long codigo) {
		if (service.excluir(codigo)) {
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@Override
	@PutMapping()
	public ResponseEntity<M> atualizar(@Valid @RequestBody M model, HttpServletResponse response) {
		var object = service.atualizar(model);

		publisher.publishEvent(new RecursoAtualizadoEvent(this, response, object.getId()));

		return ResponseEntity.ok().body(getM().gerarModel(object));
	}

	@Override
	@PostMapping()
	public ResponseEntity<M> salvar(@Valid @RequestBody M model, HttpServletResponse response) {
		var object = service.salvar(model);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, object.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(getM().gerarModel(object));
	}
	
	@Override
	@PostMapping("/consultar")
	public ResponseEntity<M> consultar(@RequestBody M model, HttpServletResponse response) {
		var object = service.findByModelOptional(model);

		if (object.isPresent()) {
			return ResponseEntity.ok(getM().gerarModel(object.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@Override
	@GetMapping("/consultarTodos")
	public ResponseEntity<List<M>> consultarTodos() {
		return ResponseEntity.ok(service.findAll());
	}

	@Override
	@GetMapping("/{codigo}")
	public ResponseEntity<M> consultar(@PathVariable Long codigo) {
		var object = service.findByIdOptional(codigo);

		if (object.isPresent()) {
			return ResponseEntity.ok(getM().gerarModel(object.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@Override
	@GetMapping("/json")
	public ResponseEntity<String> json() throws JsonProcessingException {
		
		ObjectMapper mapper = new ObjectMapper();
		JsonSchemaGenerator jsonSchemaGenerator = new JsonSchemaGenerator(mapper);
        JsonSchema jsonSchema = jsonSchemaGenerator.generateSchema(Utils.getClassType(getClass(), 1));
        jsonSchema.setId(null);
		return ResponseEntity.ok(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonSchema));
	}
	
	@Override
	@GetMapping("/page")
	public Page<M> filter(M model, Pageable pageable) {
		return service.filter(model, pageable);
	}

	@SuppressWarnings("unchecked")
	private M getM() {
		return (M) Utils.getObjetoFromClassType(getClass(), 1);
	}
}
