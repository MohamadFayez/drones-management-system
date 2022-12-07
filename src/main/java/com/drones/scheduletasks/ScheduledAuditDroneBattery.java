package com.drones.scheduletasks;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.drones.entity.Drone;
import com.drones.repository.DroneRepository;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class ScheduledAuditDroneBattery {
	
	@Autowired
	private DroneRepository droneRepository;
	
	static final Logger log = LoggerFactory.getLogger(ScheduledAuditDroneBattery.class);
	
    @Scheduled(fixedRate = 10000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        
        List<Drone> allDrones = droneRepository.findAll();
    	DecimalFormat decFormat = new DecimalFormat("#%");
    	allDrones.stream().forEach(drone->{
        	log.info("Batery level: SerialNumber  "+ drone.getSerialNumber()+"  Battery Level "+ decFormat.format(drone.getBatteryCapacity().divide(new BigDecimal(100))));
    	});
        Thread.sleep(10000);
    }
    
}
