package com.drones.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drones.entity.Drone;
import com.drones.type.State;

public interface DroneRepository extends JpaRepository<Drone, String> {

	List<Drone> findAllByState(State state);

	Drone findBySerialNumber(String serialNumber);

	default void updateState(Drone drone, State state) {
		drone.setState(State.LOADING);
		this.save(drone);
	}

}
