package com.example.demo.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "user")
public class LoginInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String name;

	
	
	
	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY,mappedBy = "user")
	private List<DriverInfo> driver;
	
	
	@JsonManagedReference
	public List<DriverInfo> getDriver() {
		return driver;
	}

	public void setDriver(List<DriverInfo> driver) {
        this.driver = driver;
    }



	
	@OneToMany(cascade={CascadeType.ALL},fetch = FetchType.EAGER,mappedBy = "user")
	private List<PassengerInfo> passenger;
	
	@JsonManagedReference
	public List<PassengerInfo> getPassenger() {
		return passenger;
	}

	public void setPassenger(List<PassengerInfo> passenger) {
        this.passenger = passenger;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
