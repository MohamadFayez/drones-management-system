package com.drones;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.drones.controller.DroneController;
import com.drones.data.request.DroneRequest;
import com.drones.data.request.LoadDroneRequest;
import com.drones.entity.Drone;
import com.drones.entity.Medication;
import com.drones.service.impl.DroneSeriviceImpl;
import com.drones.type.Model;
import com.drones.type.State;

@ExtendWith(MockitoExtension.class)
class DroneControllerTest {

	@InjectMocks
	DroneController droneMainController;
	@Mock
	DroneSeriviceImpl droneService;

	@Test
	void testRegisterDrone() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		Drone drone1 = new Drone("G3NB67");
		when(droneService.registerDrone(any(Drone.class))).thenReturn(drone1);

		DroneRequest drone2 = new DroneRequest("G3NB67", Model.CRUISERWEIGHT, 5000.0, new BigDecimal(0.75), State.DELIVERED);
		ResponseEntity<?> responseEntity = droneMainController.registerDrone(drone2);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getBody()).isEqualTo(drone1);
	}

	@Test
	void testGetAvailableDroneForLoading() {

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		List<Drone> list = new ArrayList<Drone>();
		Drone drone1 = new Drone("G3NB67", Model.CRUISERWEIGHT, 1000.0, new BigDecimal(0.80), State.IDLE);
		Drone drone2 = new Drone("S2RT5", Model.HEAVYWEIGHT, 2000.0, new BigDecimal(10), State.IDLE);
		list.add(drone1);
		list.add(drone2);

		when(droneService.getAvailabeDrones()).thenReturn(list);

		ResponseEntity<?> responseEntity = droneMainController.getAvailableDroneForLoading();
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);

	}

	@Test
	void testCheckDroneBattery() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Drone drone = new Drone("G3NB67");

		when(droneService.getBatteryLevelForGivenDrone(any(String.class))).thenReturn(drone);

		ResponseEntity<?> responseEntity = droneMainController.checkBatteryLevelForGivenDrone("G3NB67");
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getBody()).isEqualTo(drone);

	}

	@Test
	void testLoadDroneWithMedication() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Drone drone = new Drone("G3NB67");

		when(droneService.loadingDroneWithMedicationItem(any(LoadDroneRequest.class))).thenReturn(drone);

		LoadDroneRequest loadRequest = new LoadDroneRequest("SN-202T7G", "Damanhour", "Cairo", "HEROL202T7G");
		ResponseEntity<?> responseEntity = droneMainController.loadingDroneWithMedicationItem(loadRequest);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
		assertThat(responseEntity.getBody()).isEqualTo(drone);

	}

	@Test
	void testCheckLoadedMedicationItem() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		Medication medication = new Medication("M62RT", "Trintal", 100, "gt500".getBytes());

		String serialNumber = "SN-202T7G";
		when(droneService.getLoadedMedicationForADrone(serialNumber)).thenReturn(medication);

		ResponseEntity<?> responseEntity = droneMainController.loadedMedicationItemsForGivenDrone(serialNumber);
		assertThat(responseEntity.getStatusCodeValue()).isEqualTo(100);
		assertThat(responseEntity.getBody()).isEqualTo(medication);

	}

}
