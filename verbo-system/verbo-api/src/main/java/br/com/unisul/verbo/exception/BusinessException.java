package br.com.unisul.verbo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.unisul.verbo.helper.Utils;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String message, Exception ex) {
		super(message, ex);
	}

	public BusinessException(Class<?> classe) {
		this(Utils.getMensagemNaoEncontrado(classe));
	}
	
	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(FieldError field) {
		super(Utils.formatFieldError(field));
	}

}
