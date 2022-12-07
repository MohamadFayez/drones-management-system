package com.drones.exception.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.drones.type.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {
	private Status status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private List<FieldError> subErrors;

	public ErrorResponse(String message, List<FieldError> subErrors) {
		this.status = Status.Fail ;
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.subErrors = subErrors;
	}

	public ErrorResponse(String message, FieldError fieldError) {
		this.status = Status.Fail;
		this.timestamp = LocalDateTime.now();
		this.message = message;
		this.addFieldError(fieldError);
	}

	public void addFieldError(FieldError fieldError) {
		if (this.subErrors == null) {
			this.subErrors = new ArrayList<>();
		}
		this.subErrors.add(fieldError);
	}
}
