package com.drones.data.request;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import lombok.Data;
@Data
public class MedicationRequest {

public MedicationRequest() {}
	@Id
	@NotBlank(message="{constraints.medication.code.notblank}")
	@Pattern(regexp = "^[A-Z0-9_]*[A-Z0-9][A-Z0-9_]*$", flags = Flag.UNICODE_CASE,message="{constraints.medication.code.pattern}")
	@Column(name = "CODE", nullable = false)
	private String code;
	
	@NotBlank(message="{constraints.medication.name.notblank")	
	@Pattern(regexp = "^[a-zA-Z0-9_-]*[a-zA-Z0-9][a-zA-Z0-9_-]*$", flags = Flag.UNICODE_CASE,message="{constraints.medication.name.pattern}")
	@Column(name = "NAME", nullable = false)
	private String name;
	
	@NotNull(message="{constraints.medication.weight.notnull}")
	@Column(name = "WEIGHT", nullable = false)
	private Double weight;

}
