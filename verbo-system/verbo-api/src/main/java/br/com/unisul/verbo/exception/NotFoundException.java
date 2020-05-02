package br.com.unisul.verbo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotFoundException(String message, Exception ex) {
		super(message, ex);
	}

	public NotFoundException(String message) {
		super(message);
	}
}