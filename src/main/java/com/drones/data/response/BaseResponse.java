package com.drones.data.response;

import java.time.LocalDateTime;

import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.drones.type.Status;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseResponse<T> {

	public BaseResponse(String message,T data) {
		super();
        this.status = Status.Success;
		this.message = message;
		this.timestamp = LocalDateTime.now();
		this.data = data;
	}


	private Status status;
	private LocalDateTime timestamp;
	private String message;
	private T data;
	

}
