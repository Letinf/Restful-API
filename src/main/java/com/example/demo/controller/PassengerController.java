package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.domain.LoginInfo;
import com.example.demo.domain.PassengerInfo;
import com.example.demo.domain.PassengerInfoRepository;

 
@RestController
@RequestMapping("/api/passenger")
public class PassengerController {
	
	
	@Autowired
	private PassengerInfoRepository passengerRepositoy;
	
	
	@RequestMapping("getbyid")
	public PassengerInfo getPassenger1(@RequestParam int id)
	{
		PassengerInfo passengerEntity = passengerRepositoy.findPassengerInfoById(id);
	
		return passengerEntity;
		
	}
	
	@RequestMapping("getseats")
	public List<PassengerInfo> getPassenger(int seats)
	{
		List<PassengerInfo> passengerEntities = passengerRepositoy.findPassengerInfoBySeats(seats);
		
		return passengerEntities;

	}
	
	//最主要的功能，乘客发布行程 
		@Modifying
		@RequestMapping("trip")
		public PassengerInfo addPassenger(@RequestBody PassengerInfo passengerEntity)
		{
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			HttpSession session = (HttpSession) request.getSession();
			
			LoginInfo user = new LoginInfo();
			
			int userId = (int) session.getAttribute("userId");//取出session值	
			
			user.setId(userId);	
			
			passengerEntity.setUser(user);
			
			passengerRepositoy.save(passengerEntity);//前端传来的行程信息，保存起来
			
			session.setAttribute("start02", passengerEntity.getStartPosition2());//乘客起点经度
			session.setAttribute("start01", passengerEntity.getStartPosition1());//乘客起点纬度
			session.setAttribute("end02", passengerEntity.getEndPosition2());//乘客终点经度
			session.setAttribute("end01", passengerEntity.getEndPosition1());//乘客终点纬度
			
			return passengerEntity;
		}
	
		//修改订单
	@Modifying
	@RequestMapping("update")
	public PassengerInfo updatePassenger(@RequestBody PassengerInfo passengerEntity)
	{
		PassengerInfo user = passengerRepositoy.findPassengerInfoById(passengerEntity.getId());
		if (user != null)
		{
			user.setStartPlace(passengerEntity.getStartPlace());
			user.setEndPlate(passengerEntity.getEndPlate());
			user.setStartPosition1(passengerEntity.getStartPosition1());
			user.setStartPosition2(passengerEntity.getStartPosition2());
			user.setEndPosition1(passengerEntity.getEndPosition1());
			user.setEndPosition2(passengerEntity.getEndPosition2());
			user.setDepartureTimeFrom(passengerEntity.getDepartureTimeFrom());
			user.setDepartureTimeTo(passengerEntity.getDepartureTimeTo());
			user.setSeats(passengerEntity.getSeats());
	
			passengerRepositoy.save(user);
		}
		
		return user;
	}

	@Modifying
	@RequestMapping("delete/{id}")
	public void deletePassenger(@PathVariable("id") Integer id)
	{

		passengerRepositoy.deleteById(id);
	}


}
		
	
	
	
	

