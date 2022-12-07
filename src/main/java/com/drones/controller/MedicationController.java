package com.drones.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.drones.data.request.MedicationRequest;
import com.drones.data.request.mapper.MedicationRequestMapper;
import com.drones.data.response.BaseResponse;
import com.drones.entity.Medication;
import com.drones.service.MedicationService;

@RestController
@RequestMapping(path = "/medication")
@Validated
public class MedicationController {

	@Autowired
	private MedicationRequestMapper medicationRequestMapper;
	@Autowired
	private MedicationService medicationSerivce;

	@PostMapping(value = "/register", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	public ResponseEntity<?> registerMedication(@Valid @RequestPart("request") MedicationRequest request, @RequestPart("image") MultipartFile image) throws IOException {
		Medication medication = medicationRequestMapper.map(request);
		this.medicationSerivce.registerMedication(medication, image);
		BaseResponse<?> response = new BaseResponse<>("Medication created successfully", medication);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
}
