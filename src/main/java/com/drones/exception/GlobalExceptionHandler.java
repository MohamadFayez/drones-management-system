package com.drones.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.drones.exception.response.ErrorResponse;
import com.drones.exception.response.FieldError;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private MessageSource messageSource;

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		final ErrorResponse response = this.buildFieldErrors(e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(value = { RuntimeException.class })
	public ResponseEntity<Object> handleInvalidInputException(RuntimeException e) {
		e.printStackTrace();
		final ErrorResponse response = new ErrorResponse(e.getMessage(),  new ArrayList<>());
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
		final ErrorResponse response = this.buildFieldErrors(e.getBindingResult());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Unauthorized.class })
	public ResponseEntity<?> handleUnauthorizedException(Unauthorized e) {
		final ErrorResponse response = new ErrorResponse(e.getMessage(),  new ArrayList<>());
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);

	}

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<?> handleException(Exception e) {
		final ErrorResponse response = new ErrorResponse(e.getMessage(),  new ArrayList<>());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BusinessException.class)
	protected ResponseEntity<?> handleBusinessException(final BusinessException e) {
		final ErrorResponse response = this.buildErrorResponse(e.getCode());
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<?> handleEntityNotFoundException(final EntityNotFoundException e) {
		final ErrorResponse response = this.buildErrorResponse(e.getCode());
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NoSuchMessageException.class)
	protected ResponseEntity<?> handleNoSuchMessageException(final NoSuchMessageException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		final ErrorResponse response = this.buildFieldErrors(e);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	public ErrorResponse buildFieldErrors(BindingResult bindingResult) {
		final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
		List<FieldError> errors = fieldErrors.stream()
				.map(error -> new FieldError(error.getField(), error.getRejectedValue() == null ? "" : error.getRejectedValue().toString(), error.getDefaultMessage())).collect(Collectors.toList());
		return buildErrorResponse(errors);

	}

	public ErrorResponse buildFieldErrors(MethodArgumentTypeMismatchException e) {
		String value = e.getValue() == null ? "" : e.getValue().toString();
		String fieldMessage = this.getLocalizedMessage(e.getErrorCode(), null);
		List<FieldError> errors = FieldError.of(e.getName(), value, fieldMessage);
		return buildErrorResponse(errors);
	}

	public ErrorResponse buildErrorResponse(List<FieldError> errors) {
		String code = "validation.common.InvalidInputValue";
		String message = this.getLocalizedMessage(code, null);
		return new ErrorResponse(message, errors);
	}

	public ErrorResponse buildErrorResponse(String code) {
		String message = this.getLocalizedMessage(code, null);
		return new ErrorResponse(message, new ArrayList<>());
	}

	private String getLocalizedMessage(String code, Object[] params) {
		return messageSource.getMessage(code, params, Locale.ENGLISH);
	}

}
