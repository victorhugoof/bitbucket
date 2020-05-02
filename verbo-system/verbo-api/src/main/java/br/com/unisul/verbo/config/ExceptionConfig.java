package br.com.unisul.verbo.config;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.postgresql.util.PSQLException;
import org.slf4j.Logger;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.unisul.verbo.exception.AuthenticationException;
import br.com.unisul.verbo.exception.BusinessException;
import br.com.unisul.verbo.exception.CustomException;
import br.com.unisul.verbo.exception.NotFoundException;
import br.com.unisul.verbo.exception.NotImplementedException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionConfig extends ResponseEntityExceptionHandler {

	@Autowired
	private Logger log;

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		var erro = new ApiError(status, ex);

		if (ex.getMessage().contains("JSON parse error: Unrecognized field")) {
			erro = new ApiError(status, "Campo inválido", ex);
		}

		return handleExceptionInternal(ex, erro, headers, status, request);
	}

	@ExceptionHandler(AuthenticationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.UNAUTHORIZED, e.getMessage(), e));
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handleBusinessException(BusinessException e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.NOT_ACCEPTABLE, e.getMessage(), e));
	}

	@ExceptionHandler(CustomException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	public ResponseEntity<Object> handleCustomException(CustomException e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, e.getMessage(), e));
	}

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNotExistException(NotFoundException e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, e.getMessage(), e));
	}

	@ExceptionHandler(NotImplementedException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handleNotImplementedException(NotImplementedException e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.NOT_ACCEPTABLE, e));
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
	public ResponseEntity<Object> handlePSQLException(DataIntegrityViolationException e) {
		var erro = new ApiError(HttpStatus.NOT_ACCEPTABLE, e);
		String[] erros = e.getMessage().split(";");

		if (Objects.nonNull(erros[2]) && erros[2].contains("constraint")) {
			String mensagem = erros[2].replace("constraint", "").replace("[", "").replace("]", "").replace(" ", "");
			erro.setMensagem("Erro inesperado");
			erro.setMotivo("Violação de constraint: " + mensagem);
		}

		log.warn(e.getMessage(), e);
		return buildResponseEntity(erro);
	}

	@ExceptionHandler(PSQLException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handlePSQLException(PSQLException e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e));
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, criarListaDeErros(ex.getBindingResult(), status), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
			WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex,
			HttpHeaders headers, HttpStatus status, WebRequest webRequest) {
		return handleExceptionInternal(ex, new ApiError(status, ex), headers, status, webRequest);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleException(Exception e) {
		log.warn(e.getMessage(), e);
		return buildResponseEntity(new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, e));
	}

	private List<ApiError> criarListaDeErros(BindingResult bindingResult, HttpStatus status) {
		List<ApiError> erros = new ArrayList<>();

		bindingResult.getFieldErrors().stream()
				.forEach(field -> erros.add(new ApiError(status, new BusinessException(field))));

		return erros;
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}

class ApiError {

	private HttpStatus status;
	private ZonedDateTime horario;
	private String mensagem;
	private String motivo;

	private ApiError() {
		horario = ZonedDateTime.now();
	}

	ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.mensagem = ex.getLocalizedMessage();
		this.motivo = Objects.nonNull(ex.getCause()) ? ex.getCause().getLocalizedMessage() : ex.getMessage();
	}

	ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.mensagem = message;
	}

	ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.mensagem = message;
		this.motivo = Objects.nonNull(ex.getCause()) ? ex.getCause().getLocalizedMessage() : ex.getMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public ZonedDateTime getHorario() {
		return horario;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

}

class Erro {

	private String mensagemUsuario;
	private String mensagemDesenvolvedor;

	public Erro(String mensagemUsuario, String mensagemDesenvolvedor) {
		this.mensagemUsuario = mensagemUsuario;
		this.mensagemDesenvolvedor = mensagemDesenvolvedor;
	}

	public String getMensagemUsuario() {
		return mensagemUsuario;
	}

	public String getMensagemDesenvolvedor() {
		return mensagemDesenvolvedor;
	}

}