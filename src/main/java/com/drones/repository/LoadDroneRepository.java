package com.drones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drones.entity.MedicationLoad;

public interface LoadDroneRepository extends JpaRepository<MedicationLoad, String> {

	MedicationLoad findBySerialNumber(String serialno);
	
	MedicationLoad findByCode(String code);
}
