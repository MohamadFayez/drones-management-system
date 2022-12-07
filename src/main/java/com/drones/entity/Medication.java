package com.drones.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Pattern.Flag;

import lombok.Data;
@Data
@Entity
@Table(name = "MEDICATION")
public class Medication {

	public Medication() {
		
	}
	
	public Medication(String code, String name, double weight, byte[] image) {
		super();
		this.code = code;
		this.name = name;
		this.weight = weight;
		this.image = image;
	}

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
	
	@Column(name = "IMAGE", nullable = false)
	private byte[] image;
	
	@Override
	public String toString() {
		return "Medication [code=" + code + ", name=" + name + ", weight=" + weight + ",  image=" + image + "]";
	}

}
