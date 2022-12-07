package com.drones.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.drones.data.request.DroneRequest;
import com.drones.data.request.LoadDroneRequest;
import com.drones.data.request.mapper.DroneRequestMapper;
import com.drones.data.response.BaseResponse;
import com.drones.entity.Drone;
import com.drones.entity.Medication;
import com.drones.service.impl.DroneSeriviceImpl;

@RestController
@RequestMapping(path = "/drone")
@Validated
public class DroneController {
	@Autowired
	private DroneSeriviceImpl droneService;
	@Autowired
	private DroneRequestMapper droneRegisterRequestMapper;

	@PostMapping(path = "/register")
	public ResponseEntity<?> registerDrone(@Valid @NotNull @RequestBody DroneRequest dronerequest) {
		Drone drone = droneRegisterRequestMapper.map(dronerequest);
		Drone newDrone = droneService.registerDrone(drone);
		BaseResponse<?> response = new BaseResponse<>("Drone created successfully", newDrone);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "/available")
	public ResponseEntity<?> getAvailableDroneForLoading() {
		List<Drone> drones = droneService.getAvailabeDrones();
		BaseResponse<?> response = new BaseResponse<>("Available drone loaded successfully", drones);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(path = "/battery/{serialNumber}")
	public ResponseEntity<?> checkBatteryLevelForGivenDrone(@PathVariable("serialNumber") String serialNumber) {
		Drone drone = droneService.getBatteryLevelForGivenDrone(serialNumber);
		BaseResponse<?> response = new BaseResponse<>("Battery level checked successfully", drone);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(path = "/load")
	public ResponseEntity<?> loadingDroneWithMedicationItem(@Valid @NotNull @RequestBody LoadDroneRequest loadrequest) {
		Drone drone = droneService.loadingDroneWithMedicationItem(loadrequest);
		BaseResponse<?> response = new BaseResponse<>("Drone loaded with medication items successfully", drone);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping(path = "items/{serialNumber}")
	public ResponseEntity<?> loadedMedicationItemsForGivenDrone(@PathVariable("serialNumber") String serialNumber) {
		Medication medication = droneService.getLoadedMedicationForADrone(serialNumber);
		BaseResponse<?> response = new BaseResponse<>("Medication item loaded successfully", medication);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping(path = "/deliver/{serialNumber}")
	public ResponseEntity<?> deliverLoadedItems(@NotNull @PathVariable("serialNumber") String serialNumber) {
		Drone drone = droneService.deliverLoadedItems(serialNumber);
		BaseResponse<?> response = new BaseResponse<>("Drone delivered successfully", drone);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

}
