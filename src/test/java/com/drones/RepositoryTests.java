package com.drones;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.drones.entity.Drone;
import com.drones.repository.DroneRepository;
import com.drones.type.Model;
import com.drones.type.State;

import org.assertj.core.api.Assertions;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepositoryTests {
	@Autowired
	DroneRepository droneRepository;
	
	@Test
	void testAddnewDrone() {
		Drone drone = new Drone("SN-202T7G", Model.HEAVYWEIGHT, 1200.0, new BigDecimal(0.55), State.IDLE);
		droneRepository.save(drone);
		
	    droneRepository.deleteAll();
	    Assertions.assertThat(droneRepository.findAll()).isEmpty();

	}
}
