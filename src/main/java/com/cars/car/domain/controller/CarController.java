package com.cars.car.domain.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cars.car.domain.Car;
import com.cars.car.domain.CarRepository;

@Controller
public class CarController {

	@Autowired
	private CarRepository repository;

	@GetMapping("/signup")
	public String showSignUpForm(Car car) {
		return "add-car";
	}

	@GetMapping("/index")
	public String getAllCars(Model model) {
		model.addAttribute("cars", repository.findAll());
		return "index";
	}

	@PostMapping(value = "/new")
	public String createCar(Car car, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-car";
		}

		repository.save(car);
		return "redirect:/index";
	}

	@PostMapping(value = "/update/{id}")
	public String updateCar(@PathVariable("id") long id, Car car, BindingResult result, Model model) {

		if (result.hasErrors()) {
			car.setId(id);
			return "update-car";
		}

		repository.save(car);
		return "redirect:/index";
	}

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") long id, Model model) {
		Car car = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));

		model.addAttribute("car", car);
		return "update-car";
	}

	@GetMapping(value = "/{id}")
	public String getCar(@PathVariable Long id,Model model) {
		
		Car car = null;
		try {
			 car = repository.getById(id);
			}catch(Exception e) {
				e.printStackTrace();
				return "Not Found";
			}
		model.addAttribute("car",car);
		return "view-car";
	
	}

	@GetMapping(value = "/delete/{id}")
	public String deleteCar(@PathVariable("id") long id, Model model) {
		Car car = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid car Id:" + id));
		repository.delete(car);
		return "redirect:/index";
	}
}
