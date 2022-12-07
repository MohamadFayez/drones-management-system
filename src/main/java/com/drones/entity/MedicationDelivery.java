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
@Table(name = "MEDICATION_DELIVERY")
public class MedicationDelivery {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "DELIVERY_ID")
	private Integer deliveryId;

	@Column(name = "DELIVERY_TIME")
	private LocalDateTime deliveryTime;

	@OneToOne(targetEntity = MedicationLoad.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "LOAD_ID", referencedColumnName = "LOAD_ID")
	private MedicationLoad medicationLoad;

}
