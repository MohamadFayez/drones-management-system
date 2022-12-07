package com.drones.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.drones.entity.Medication;
import com.drones.repository.MedicationRepository;
import com.drones.service.MedicationService;
import com.drones.util.ImageUtil;

@Service
public class MedicationServiceImpl implements MedicationService {

	@Autowired
	private MedicationRepository medicationRepository;

	@Override
	public Medication registerMedication(Medication medication, MultipartFile file) throws IOException {
		byte[] imagebytes = ImageUtil.processImage(file);
		medication.setImage(imagebytes);
		return this.medicationRepository.save(medication);
	}

}
