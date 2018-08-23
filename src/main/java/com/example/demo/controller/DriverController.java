package com.example.demo.controller;

/*
 * write by Supermantom
 * 2018/8/14
 */
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

import com.example.demo.domain.DriverInfo;
import com.example.demo.domain.DriverInfoRepository;
import com.example.demo.domain.LoginInfo;

 
@RestController
@RequestMapping("/api/driver")
public class DriverController {
	

	
	@Autowired
	private DriverInfoRepository driverRepositoy;
	
	//通过id查找司机行程
	@RequestMapping("getbyid")
	public DriverInfo getPassenger(@RequestParam int id)
	{
		DriverInfo driverEntity = driverRepositoy.findDriverInfoById(id);

		return driverEntity;
		
	}
	//通过座位查找司机行程
	@RequestMapping("getbyseats")
	public List<DriverInfo> getDriver( int seats)
	{
		List<DriverInfo> driverEntities = driverRepositoy.findDriverInfoBySeats(seats);
		
		return driverEntities;
	
	}
	
	//最主要的功能，前端信息写入司机订单表
		@Modifying
		@RequestMapping("trip")
		public DriverInfo addDriver(@RequestBody DriverInfo driverEntity)
		{
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			HttpSession session = (HttpSession) request.getSession();
			
			LoginInfo user = new LoginInfo();
			
			int userId = (int) session.getAttribute("userId");//取出session值	
			
			user.setId(userId);	
			
			driverEntity.setUser(user);
			
			driverRepositoy.save(driverEntity);
			
			session.setAttribute("start2", driverEntity.getStartPosition2());//司机起点经度
			session.setAttribute("start1", driverEntity.getStartPosition1());//司机起点纬度
			session.setAttribute("end2", driverEntity.getEndPosition2());//司机终点经度
			session.setAttribute("end1", driverEntity.getEndPosition1());//司机终点纬度
			 
			 return driverEntity;
			
		}
		
		
		
		//修改行程信息
	@Modifying
	@RequestMapping("update")
	public DriverInfo updateDriver(@RequestBody DriverInfo driverEntity)
	{
		DriverInfo user = driverRepositoy.findDriverInfoById(driverEntity.getId());//通过映射实体的方法来更新操作
		if (user != null)
		{									   //如果想要仅更新几个字段，可以 让前端跳该修改界面时保留全部字段，然后可以修改某个字段，再POST过来
			user.setStartPlace(driverEntity.getStartPlace());
			user.setEndPlate(driverEntity.getEndPlate());
			user.setStartPosition1(driverEntity.getStartPosition1());
			user.setStartPosition2(driverEntity.getStartPosition2());
			user.setEndPosition1(driverEntity.getEndPosition1());
			user.setEndPosition2(driverEntity.getEndPosition2());
			user.setDepartureTimeFrom(driverEntity.getDepartureTimeFrom());
			user.setDepartureTimeTo(driverEntity.getDepartureTimeTo());
			user.setSeats(driverEntity.getSeats());
	
			driverRepositoy.save(user);
		}
		
		return user;
	}

	
	//删除行程
	@Modifying
	@RequestMapping("delete/{id}")
	public void deletePassenger(@PathVariable("id") Integer id)
	{
		driverRepositoy.deleteById(id);
	}


}
		
	
	
	
	

