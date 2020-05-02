package br.com.unisul.verbo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class NotImplementedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotImplementedException(String message, Exception ex) {
		super(message, ex);
	}

	public NotImplementedException(String message) {
		super(message);
	}

	public NotImplementedException() {
		this("Método não implementado");
	}
}
