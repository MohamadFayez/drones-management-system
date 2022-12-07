package com.drones.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.drones.entity.Medication;

public interface MedicationService {
	
	public Medication registerMedication(Medication medication,MultipartFile file) throws IOException;

}
