package com.drones.data.request.mapper;

import org.mapstruct.Mapper;

import com.drones.data.request.DroneRequest;
import com.drones.entity.Drone;

@Mapper(componentModel = "spring")
public interface DroneRequestMapper {

	Drone map(DroneRequest droneRegisterRequest);

	DroneRequest unmap(Drone drone);
}
