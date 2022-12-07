package com.drones.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.drones.entity.MedicationDelivery;

public interface DroneDeliveryRepository extends JpaRepository<MedicationDelivery, String> {

}
