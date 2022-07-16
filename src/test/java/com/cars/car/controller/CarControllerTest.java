package com.cars.car.controller;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.cars.car.domain.Car;
import com.cars.car.domain.controller.CarController;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerTest {

	@Autowired
	private CarController controller;

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("GET Status 2xx - Working")
	public void testGetStatus200() throws Exception {
		MvcResult result =  mockMvc.perform(get("/")).andExpect(status().is2xxSuccessful()).andReturn();
		String content = String.valueOf(result.getResponse().getStatus());
		assertTrue(content.contains("200"));
	}
	
	@Test
	@DisplayName("GET Signup View - Working")
	public void testReturnSignupView() throws Exception {
		ModelAndViewAssert.assertViewName(mockMvc.perform(get("/signup")).andReturn().getModelAndView(), "add-car");
	}
	
	@Test
	@DisplayName("GET Index View - Working")
	public void testReturnIndexView() throws Exception {
		ModelAndViewAssert.assertViewName(mockMvc.perform(get("/index")).andReturn().getModelAndView(), "index");
	}
	
	@Test
	@DisplayName("Update Car View - Working")
	public void testReturnUpdateCarView() throws Exception {
		ModelAndViewAssert.assertViewName(mockMvc.perform(get("/edit/140")).andReturn().getModelAndView(), "update-car");
	}
	
	@Test
	@DisplayName("Get Car by Id View - Working")
	public void testReturnGetCarIdView() throws Exception {
		ModelAndViewAssert.assertViewName(mockMvc.perform(get("/140")).andReturn().getModelAndView(), "view-car");
	}
	
	@Test
	@DisplayName("Delete Car by Id View - Working")
	public void testReturnDeleteCarIdView() throws Exception {
		ModelAndViewAssert.assertViewName(mockMvc.perform(get("/delete/101")).andReturn().getModelAndView(), "redirect:/index");
	}
	
	
	@Test
	@DisplayName("GET by ID - Found")
	void testGetCarByIdFound() throws Exception { // CHange id if not there.

		MvcResult result = mockMvc.perform(get("/{id}", 3))

				.andExpect(status().isOk()).andReturn();
		String content = result.getResponse().getContentAsString();
		assertNotNull(content);
		assertTrue(content.contains("Hyundai"));
	}
	
	/*
	 * @Test
	 * 
	 * @DisplayName("GET by ID - Not Found") void testGetCarByIdNotFound() throws
	 * Exception { // CHange id if not there.
	 * 
	 * MvcResult result = mockMvc.perform(get("/{id}", 1))
	 * 
	 * .andExpect(status().isNotFound()).andReturn(); String content =
	 * result.getResponse().getContentAsString(); assertNull(content);
	 * //assertTrue(content.contains("Empty")); }
	 */


	@Test
	@DisplayName("GET /index")
	void testGetAllCars() throws Exception {

		MvcResult result = (MvcResult) this.mockMvc.perform(get("/index").accept(MediaType.APPLICATION_JSON))
				.andReturn();

		String content = result.getResponse().getContentAsString();
		assertNotNull(content);
		assertTrue(content.contains("Hyundai"));
	}

	
	@Test
	@DisplayName("POST /signup ")
	void testCreateCar() throws Exception {
		// Setup mocked service
		Car car = new Car("Maruti", "10");
		
		ResultActions s = mockMvc.perform(get("/signup").contentType(MediaType.APPLICATION_JSON).content(asJsonString(car)))
					.andExpect(status().isOk());
				
	}
	
	@Test
	@DisplayName("PUT /edit/1 - ")
	void testCarEdit() throws Exception {
		// Setup mocked service
		Car mockCar = new Car(3, "Hyundai", "100");

		MvcResult result = (MvcResult) mockMvc.perform(get("/edit/{id}", 3,asJsonString(mockCar)).contentType(MediaType.APPLICATION_JSON)).andReturn();
		
		String content = result.getModelAndView().getViewName().toString();
		assertNotNull(content);
		assertTrue(content.contains("update-car"));
				
	}
	
	@Test
	@DisplayName("DELETE /car/1 - Success")
	void testProductDeleteSuccess() throws Exception {
		
		MvcResult result = (MvcResult) mockMvc.perform(get("/delete/{id}", 540).contentType(MediaType.APPLICATION_JSON))
				.andReturn();
		String content = result.getModelAndView().getViewName().toString();
		assertNotNull(content);
		assertTrue(content.contains("redirect:/index"));
	
	}
	
	static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
