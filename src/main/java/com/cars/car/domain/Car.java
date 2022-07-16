package com.cars.car.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Car {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String price;

    public Car(){
    }
    
    public Car(String name,String price){
    	this.name = name;
    	this.price = price;
    }
    
    public Car(long id,String name,String price){
    	this.id= id;
    	this.name = name;
    	this.price = price;
    }

    public Car(String name){
        this.name = name;
    }

    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}


}
