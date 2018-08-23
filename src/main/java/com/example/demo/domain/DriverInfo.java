package com.example.demo.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "driver_order")
public class DriverInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@NotNull
	private String startPlace;

	private String endPlate;

	private Double startPosition1;

	private Double startPosition2;

	private Double endPosition1;

	private Double endPosition2;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date departureTimeFrom;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date departureTimeTo;

	private int seats;

	@ManyToOne
	@JoinColumn(name = "user_id")

	private LoginInfo user;

	@JsonBackReference
	public LoginInfo getUser() {
		return user;
	}

	public void setUser(LoginInfo user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlate() {
		return endPlate;
	}

	public void setEndPlate(String endPlate) {
		this.endPlate = endPlate;
	}

	public Double getStartPosition1() {
		return startPosition1;
	}

	public void setStartPosition1(Double startPosition1) {
		this.startPosition1 = startPosition1;
	}

	public Double getStartPosition2() {
		return startPosition2;
	}

	public void setStartPosition2(Double startPosition2) {
		this.startPosition2 = startPosition2;
	}

	public Double getEndPosition1() {
		return endPosition1;
	}

	public void setEndPosition1(Double endPosition1) {
		this.endPosition1 = endPosition1;
	}

	public Double getEndPosition2() {
		return endPosition2;
	}

	public void setEndPosition2(Double endPosition2) {
		this.endPosition2 = endPosition2;
	}

	public Date getDepartureTimeFrom() {
		return departureTimeFrom;
	}

	public void setDepartureTimeFrom(Date departureTimeFrom) {
		this.departureTimeFrom = departureTimeFrom;
	}

	public Date getDepartureTimeTo() {
		return departureTimeTo;
	}

	public void setDepartureTimeTo(Date departureTimeTo) {
		this.departureTimeTo = departureTimeTo;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

}
