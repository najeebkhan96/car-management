package com.cars.car;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.cars.car.domain.controller.CarController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CarApplicationTestsIT {

	@Autowired
	CarController carController;
	
	@Test
	void contextLoads() {
		assertNotNull(carController);
	}

}
