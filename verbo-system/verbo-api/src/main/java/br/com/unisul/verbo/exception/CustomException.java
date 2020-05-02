package br.com.unisul.verbo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomException(String message, Exception ex) {
		super(message, ex);
	}

	public CustomException(String message) {
		super(message);
	}
}