package com.drones.data.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class LoadDroneRequest {

	public LoadDroneRequest() {
		
	}
	
	public LoadDroneRequest(@NotNull @NotBlank String serialNumber, @NotNull @NotBlank String source,
			@NotNull @NotBlank String destination, @NotNull @NotBlank String code) {
		super();
		this.serialNumber = serialNumber;
		this.source = source;
		this.destination = destination;
		this.code = code;
	}

	@NotBlank(message="{constraints.drone.serialnumber.notnull}")
	@NotNull(message="{constraints.drone.serialnumber.notblank}")
	private String serialNumber;
	
	@NotNull(message= "{constraints.load.source.notnull}")
	@NotBlank(message= "{constraints.load.source.notblank}")
	private String source;
	
	@NotNull(message= "{constraints.load.destination.notnull}")
	@NotBlank(message= "{constraints.load.destination.notblank}")
	private String destination;
	
	@NotNull(message="{constraints.medication.code.notnull}")
	@NotBlank(message="{constraints.medication.code.notblank}")
	private String code;
}
