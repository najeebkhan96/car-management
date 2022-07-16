package com.cars.car.it;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cars.car.domain.Car;
import com.cars.car.domain.CarRepository;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CarWebControllerIT {
	
	@Autowired
	CarRepository carRepository;
	
	@LocalServerPort
	private int port;

	private WebDriver driver;

	private String baseUrl;
	
	@Before
	public void setup() {
		
		baseUrl = "http://localhost:" + port + "/index";
		// always start with an empty database
		//carRepository.deleteAll();
		//carRepository.flush();
	}
	
	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	public void testHomePage() {
		baseUrl = "http://localhost:" + port + "/index";
		Car testCar =
			carRepository.save(new Car("test car", "1000"));
		driver = new HtmlUnitDriver();
		driver.get(baseUrl);

		// the table shows the test car
		assertThat(driver.findElement(By.id("car_table")).getText()).
			contains("test car", "1000");

		// the "Edit" link is present with href containing /edit/{id}
		driver.findElement
			(By.cssSelector
				("a[href*='/edit/" + testCar.getId() + "']"));
	}
	
	@Test
	public void testEditPageCar() throws Exception {
		baseUrl = "http://localhost:" + port + "/edit";
		driver = new HtmlUnitDriver();
		Car testCar =
				carRepository.save(new Car("Mercedes", "2000"));
		driver.get(baseUrl + "/" + testCar.getId());

		driver.findElement(By.name("name")).sendKeys("S Class");
		driver.findElement(By.name("price")).sendKeys("0000");
		driver.findElement(By.name("btn_submit")).click();

		assertThat(carRepository.findById(testCar.getId()).get().getPrice())
			.isEqualTo("20000000");
	}
	
	
	@Test
	public void testEditPageUpdateCar() throws Exception {
		baseUrl = "http://localhost:" + port;
		Car testCar =
			carRepository.save(new Car("Tesla", "100000"));
		driver = new HtmlUnitDriver();

		driver.get(baseUrl + "/edit/" + testCar.getId());

		final WebElement nameField = driver.findElement(By.name("name"));
		nameField.clear();
		nameField.sendKeys("modified Tesla");
		final WebElement salaryField = driver.findElement(By.name("price"));
		salaryField.clear();
		salaryField.sendKeys("200000");
		driver.findElement(By.name("btn_submit")).click();

		assertThat(carRepository.findById(testCar.getId()).get().getPrice())
			.isEqualTo("200000");
	}
	

	
}
