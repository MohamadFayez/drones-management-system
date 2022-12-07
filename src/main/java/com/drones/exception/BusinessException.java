package com.drones.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String code;
	private Object[] params;

	public BusinessException(String code, Object[] params) {
		this.code = code;
		this.params = params;
	}

}
