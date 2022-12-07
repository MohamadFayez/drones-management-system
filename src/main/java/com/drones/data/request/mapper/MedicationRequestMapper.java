package com.drones.data.request.mapper;

import org.mapstruct.Mapper;

import com.drones.data.request.MedicationRequest;
import com.drones.entity.Medication;
@Mapper(componentModel = "spring")

public interface MedicationRequestMapper {
	
 Medication	map(MedicationRequest request);

}
