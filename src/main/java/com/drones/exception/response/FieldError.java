package com.drones.exception.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class FieldError {
	private String field;
	private Object rejectedValue;
	private String message;

	public FieldError(String field, Object rejectedValue, String message) {
		this.field = field;
		this.rejectedValue = rejectedValue;
		this.message = message;
	}

	public static List<FieldError> of(String field, Object rejectedValue, String message) {
		List<FieldError> fieldErrors = new ArrayList<>();
		fieldErrors.add(new FieldError(field, rejectedValue, message));
		return fieldErrors;
	}
}