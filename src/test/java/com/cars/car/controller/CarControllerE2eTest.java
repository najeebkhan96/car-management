package com.cars.car.controller;

import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;


public class CarControllerE2eTest {

	private static int port = Integer.parseInt(System.getProperty("server.port", "8080"));
	private String baseUrl = "http://localhost:" + port;
	private String addCarUrl = baseUrl + "/signup";
	private String listAllCarsUrl = baseUrl + "/index";
	private String addNewCarUrl = baseUrl + "/new";

	private WebDriver driver;
	
	@BeforeClass
	public static void setupClass() {
		// setup Chrome Driver
		WebDriverManager.chromedriver().setup();
	}

	@Before
	public void setup() {
		baseUrl = "http://localhost:" + port;
		driver = new ChromeDriver();
	}

	@After
	public void teardown() {
		driver.quit();
	}
	
	@Test
	void testCreateCar() {
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(addCarUrl);
		
		driver.findElement(By.name("name")).sendKeys("TestCarE2e");
		driver.findElement(By.name("price")).sendKeys("1000000");
		
		driver.findElement(By.name("btn_submit")).click();
		
		driver.get(listAllCarsUrl);
		
		//String suggestionTableText = driver.findElement(By.id("suggestions_table")).getText();
		String name = driver.findElement(By.id("car_table")).getText();
		
		assertTrue(name.contains("TestCarE2e"));
		
	}
	
	@Test
	void testEditCar() {
		System.setProperty("webdriver.chrome.driver", "chromedriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://localhost:8080/edit/2133");
		
		/*
		 * driver.findElement (By.cssSelector("a[href*='/edit/2133']")) .click();
		 */
		final WebElement nameField = driver.findElement(By.name("name"));
		nameField.clear();
		nameField.sendKeys("modified Car");
		
		final WebElement priceField = driver.findElement(By.name("price"));
		priceField.clear();
		priceField.sendKeys("125478963");
		
		driver.findElement(By.name("btn_submit")).click();
		
		driver.get(listAllCarsUrl);
		
		String name = driver.findElement(By.id("car_table")).getText();
		
		assertTrue(name.contains("modified Car"));
	}
}
