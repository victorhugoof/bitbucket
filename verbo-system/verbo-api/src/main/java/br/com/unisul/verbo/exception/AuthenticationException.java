package br.com.unisul.verbo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthenticationException(String message, Exception ex) {
		super(message, ex);
	}

	public AuthenticationException(String message) {
		super(message);
	}
}