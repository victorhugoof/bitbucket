package br.com.unisul.verbo.event.impl;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.unisul.verbo.event.RecursoAtualizadoEvent;

@Component
public class RecursoAtualizadoEventImpl implements ApplicationListener<RecursoAtualizadoEvent> {

	@Override
	public void onApplicationEvent(RecursoAtualizadoEvent recursoAtualizadoEvent) {
		HttpServletResponse response = recursoAtualizadoEvent.getResponse();
		Long codigo = recursoAtualizadoEvent.getCodigo();
		
		adicionarHeaderLocation(response, codigo);
	}

	private void adicionarHeaderLocation(HttpServletResponse response, Long codigo) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(codigo).toUri();
		response.setHeader("Location", uri.toASCIIString());
	}

}
