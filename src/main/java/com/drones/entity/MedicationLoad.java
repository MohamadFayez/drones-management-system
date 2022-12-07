package com.drones.entity;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "MEDICATION_LOAD")
public class MedicationLoad {

	public MedicationLoad() {

	}

	public MedicationLoad(Integer loadId, String source, String destination, LocalDateTime creationDate, Drone drone, Medication medication) {
		super();
		this.loadId = loadId;
		this.source = source;
		this.destination = destination;
		this.creationDate = creationDate;
		this.drone = drone;
		this.medication = medication;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "LOAD_ID")
	private Integer loadId;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "DESTINATION")
	private String destination;

	@Column(name = "CREATION_DATE")
	private LocalDateTime creationDate;

	@Column(name = "SERIAL_NUMBER_FK",updatable = false,insertable = false)
	private String serialNumber;
	
	@Column(name = "CODE_FK",updatable = false,insertable = false)
	private String code;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "SERIAL_NUMBER_FK", referencedColumnName = "SERIAL_NUMBER")
	private Drone drone;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "CODE_FK", referencedColumnName = "CODE")
	private Medication medication;

}
