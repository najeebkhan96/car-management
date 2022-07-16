package com.cars.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cars.car.domain.Car;
import com.cars.car.domain.CarRepository;



@SpringBootApplication
public class CarApplication implements CommandLineRunner{

	@Autowired
    private CarRepository repository;
	
	public static void main(String[] args) {
		SpringApplication.run(CarApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
        for (int i = 0; i < 5; i++) {
            repository.save(new Car("My car number #" + (i+1)));
        }
    }
}
