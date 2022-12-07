package com.drones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.drones.entity.Drone;
import com.drones.repository.DroneRepository;
import com.drones.service.impl.DroneSeriviceImpl;
import com.drones.type.Model;
import com.drones.type.State;

@ExtendWith(MockitoExtension.class)
class ServiceTests {

	@InjectMocks
	DroneSeriviceImpl droneServiceImpl;
	@Mock
	DroneRepository droneRepository;
	
	@Test
	void testGetAvailabeDrones() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		List<Drone> list = new ArrayList<Drone>();
		Drone drone1 = new Drone("SN-24527X", Model.MIDDLEWEIGHT, 1200.0, new BigDecimal(0.95), State.IDLE);
		Drone drone2 = new Drone("SN-202T7G", Model.LIGHTWEIGHT, 1500.0, new BigDecimal(1), State.IDLE);
		Drone drone3 = new Drone("SN-2092R4", Model.CRUISERWEIGHT, 2000.0, new BigDecimal(0.98), State.IDLE);
		Drone drone4 = new Drone("SN-20827T", Model.HEAVYWEIGHT, 2000.0, new BigDecimal(0.98), State.IDLE);
		list.add(drone1);
		list.add(drone2);
		list.add(drone3);
		list.add(drone4);
		

		when(droneRepository.findAllByState(State.IDLE)).thenReturn(list);

		List<Drone> availableDrones = droneServiceImpl.getAvailabeDrones();
		assertEquals(4, availableDrones.size()); 
		verify(droneRepository, times(1)).findAllByState(State.IDLE);
	}

}
