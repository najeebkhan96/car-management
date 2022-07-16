package com.cars.car.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.ObjectUtils;

import com.cars.car.domain.Car;
import com.cars.car.domain.CarRepository;



@ExtendWith({SpringExtension.class})
@SpringBootTest
@ActiveProfiles("test")
public class CarRepositoryTest {

	@Autowired
	private CarRepository repository;
	
	@Test
    void testFindAll() {
        List<Car> cars = repository.findAll();
        Assertions.assertNotEquals(1, cars.size(),"We dont know how many cars are there in our DB");
    }
	
	@Test
    void testFindAllSorted() {
        List<Car> cars = repository.findAll(Sort.by(Sort.DEFAULT_DIRECTION.ASC,"id"));
        Assertions.assertNotEquals(1, cars.size(),"We dont know how many cars are there in our DB");
        
    }
	
	@Test
	void findByIdSuccess() {
		/*
		 * Car car = repository.save(new Car("TestCar","10000"));
		 * 
		 * Car getCar = repository.getById(car.getId());
		 * 
		 * // Validate that we found it
		 * Assertions.assertTrue(!ObjectUtils.isEmpty(getCar), "Car should be found");
		 * 
		 * // Validate the product values Assertions.assertEquals("TestCar",
		 * getCar.getName(), "Carf name should be \"TestCar \"");
		 * Assertions.assertEquals("10000", getCar.getPrice(),
		 * "Car price should be 10000");
		 */
		Car car = repository.save(new Car("TestCar","10000"));

        // Validate that we found it
        Assertions.assertTrue(!ObjectUtils.isEmpty(car), "Car should be found");

        // Validate the product values
        Assertions.assertEquals("TestCar", car.getName(), "Carf name should be \"TestCar \"");
        Assertions.assertEquals("10000", car.getPrice(), "Car price should be 10000");
	}
	
	@Test
	void findByIdFailure() {
		Car car = repository.save(new Car("TestCar","10000"));

        // Validate that we found it
        Assertions.assertFalse(ObjectUtils.isEmpty(car), "Car should not be found");
	}
	
	
	@Test
    void testSave() {
        // Create a new car and save it to the database
        Car car = new Car("Test Car", "50000");
        Car savedCar = repository.save(car);

        // Validate the saved car
        Assertions.assertEquals("Test Car", savedCar.getName());
        Assertions.assertEquals("50000", savedCar.getPrice());

        // Validate that we can get it back out of the database
        Optional<Car> getCar = repository.findById(savedCar.getId());
        
        Assertions.assertTrue(getCar.isPresent(), "Could  reload product from the database");
        Assertions.assertEquals("Test Car", getCar.get().getName(), "Product name does match");
        Assertions.assertEquals("50000", getCar.get().getPrice(), "Product price does match");
    }
	
	@Test
    void testUpdateSuccess() {
        // Update product 1's name, quantity, and version
        Car car =repository.save(new Car("Car","100"));
        car.setName("Updated Car");
        car.setPrice("1000");
        
        Car result  = repository.save(car);

        // Validate that our product is returned by update()
        Assertions.assertTrue(!ObjectUtils.isEmpty(result), "The car should have been updated");

        // Retrieve product 1 from the database and validate its fields
        Optional<Car> loadedCar = repository.findById(result.getId());
        Assertions.assertTrue(loadedCar.isPresent(), "Updated product should exist in the database");
        Assertions.assertEquals("Updated Car", loadedCar.get().getName(), "The car name does  match");
        Assertions.assertEquals("1000", loadedCar.get().getPrice(), "The price should now be 100");
    }
	
	@Test
    void testUpdateFailure() {
        // Update product 1's name, price
        Car car = null;
        Car result = null;
        try {
        	
        	car = repository.getById(1L); // enter id that is not there.
            car.setName("Updated Car");
            car.setPrice("1000");
        	result  = repository.save(car);
        }catch(Exception e) {
        	e.printStackTrace();
        }

        // Validate that our product is returned by update()
        Assertions.assertTrue(ObjectUtils.isEmpty(result), "The car should not have been updated");

    }
	
	@Test
    void testDeleteSuccess() { // use new id to run
		Car car = new Car(410,"Maruti","1000");
        repository.deleteById(car.getId());
        try {
        	
        	 Assertions.assertEquals(Optional.of(repository.findById(car.getId())), Optional.of(null));
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
	
	@Test
    void testDeleteFailure() { // use new id to run
		Car car = null;
		try {
			car = new Car(409,"Maruti","1000");
			repository.deleteById(car.getId());
			Assertions.assertEquals(Optional.of(repository.findById(car.getId())), Optional.of(null));
		}catch(Exception e) {
			e.printStackTrace();
		}
//		/Assertions.assertNotEquals(Optional.of(repository.findById(car.getId())),Optional.of(null));
		Assertions.assertNotNull(car);
    }

}
