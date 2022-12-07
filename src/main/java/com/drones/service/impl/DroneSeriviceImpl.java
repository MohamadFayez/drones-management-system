package com.drones.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drones.data.request.LoadDroneRequest;
import com.drones.entity.Drone;
import com.drones.entity.Medication;
import com.drones.entity.MedicationDelivery;
import com.drones.entity.MedicationLoad;
import com.drones.exception.BusinessException;
import com.drones.repository.DroneDeliveryRepository;
import com.drones.repository.DroneRepository;
import com.drones.repository.LoadDroneRepository;
import com.drones.repository.MedicationRepository;
import com.drones.service.DroneService;
import com.drones.type.State;

@Service
public class DroneSeriviceImpl implements DroneService {

	@Autowired
	private DroneRepository droneRepository;
	@Autowired
	private MedicationRepository medicationRepository;
	@Autowired
	private LoadDroneRepository loadDroneRepository;
	@Autowired
	private DroneDeliveryRepository droneDeliveryRepository;

	@Override
	public Drone registerDrone(Drone drone) {
		return droneRepository.save(drone);
	}

	@Override
	public List<Drone> getAvailabeDrones() {
		return droneRepository.findAllByState(State.IDLE);
	}

	@Override
	public Drone getBatteryLevelForGivenDrone(String serialNumber) {

		Drone newdrone = new Drone();
		newdrone.setSerialNumber(serialNumber);
		Drone drone = droneRepository.findBySerialNumber(newdrone.getSerialNumber());
		if (drone == null) {
			throw new BusinessException("validation.drone.notfound", new Object[] { serialNumber });
		}
		return drone;
	}

	@Override
	public Medication getLoadedMedicationForADrone(String serialNumber) {

		MedicationLoad loadMedication = loadDroneRepository.findBySerialNumber(serialNumber);
		if (loadMedication == null) {
			throw new BusinessException("exception.medication.notfound", new Object[] { serialNumber });
		}

		return loadMedication.getMedication();
	}

	@Override
	public Drone loadingDroneWithMedicationItem(LoadDroneRequest loadRequest) {
		this.beforeLoadingInitialData();
		Drone drone = droneRepository.findBySerialNumber(loadRequest.getSerialNumber());

		Medication medication = medicationRepository.findByCode(loadRequest.getCode());
		MedicationLoad checkLoad = loadDroneRepository.findByCode(loadRequest.getCode());

		if (checkLoad != null) {
			throw new BusinessException("validation.medication.code.notexist", null);

		}

		if (drone == null) {
			throw new BusinessException("validation.drone.notexist", null);
		}

		if (medication == null) {
			throw new BusinessException("validation.medication.notexist", null);
		}

		if (drone.getWeightLimit() < medication.getWeight()) {
			throw new BusinessException("validation.drone.exceedLimit", new Object[] { loadRequest.getSerialNumber(), drone.getBatteryCapacity() });
		}
		if (drone.getBatteryCapacity().compareTo(BigDecimal.valueOf(0.25)) < 0) {
			throw new BusinessException("validation.drone.batteryLow", new Object[] { loadRequest.getSerialNumber() });
		}

		this.droneRepository.updateState(drone, State.LOADING);

		MedicationLoad loadMedication = new MedicationLoad();
		loadMedication.setDrone(drone);
		loadMedication.setMedication(medication);
		loadMedication.setSource(loadRequest.getSource());
		loadMedication.setDestination(loadRequest.getDestination());
		loadMedication.setCreationDate(LocalDateTime.now());
		loadDroneRepository.save(loadMedication);

		this.droneRepository.updateState(drone, State.LOADED);

		return drone;
	}

	@Override
	public Drone deliverLoadedItems(String serialNumber) {
		Drone drone = droneRepository.findBySerialNumber(serialNumber);
		this.droneRepository.updateState(drone, State.DELIVERING);
		MedicationLoad loadMedication = loadDroneRepository.findBySerialNumber(serialNumber);

		if (loadMedication == null) {
			throw new BusinessException("validation.drone.notAvailable", new Object[] { serialNumber });
		}

		MedicationDelivery newdelivery = new MedicationDelivery();
		newdelivery.setMedicationLoad(loadMedication);
		newdelivery.setDeliveryTime(LocalDateTime.now());
		droneDeliveryRepository.save(newdelivery);

		this.droneRepository.updateState(drone, State.DELIVERED);

		return drone;
	}

	private void beforeLoadingInitialData() {
		List<Medication> allMedication = Arrays.asList(new Medication("MH112", "CAIRx", 100, "sade23Rd".getBytes()), new Medication("MES1158", "EALe5", 150, "sade2Y4d".getBytes()),
				new Medication("DFHO52", "Damn85", 200, "sade2U4d".getBytes()), new Medication("EDLM96", "SA542", 300, "sade2Q4d".getBytes()), new Medication("LDLF52", "M8ham5", 400, "sa3e234d".getBytes()));
		medicationRepository.saveAll(allMedication);

	}

}
