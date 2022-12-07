package com.drones.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.drones.data.request.LoadDroneRequest;
import com.drones.entity.Drone;
import com.drones.entity.Medication;

@Component
public interface DroneService {
	
	Drone registerDrone(Drone drone);

	Drone getBatteryLevelForGivenDrone(String serialNumber) throws Exception;
	
	Medication getLoadedMedicationForADrone(String serialNumber);
	
	List<Drone> getAvailabeDrones();
	
	Drone loadingDroneWithMedicationItem(LoadDroneRequest loadRequest);
	
	Drone deliverLoadedItems(String serialNumber);
	
}
