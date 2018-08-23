package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demo.domain.LoginInfo;
import com.example.demo.domain.LoginInfoRepository;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginInfoRepository loginRepository;
	
	
	@RequestMapping("name")
	public String gets( String name)
	{
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpSession session = (HttpSession) request.getSession();
		List<LoginInfo> loginEntities = loginRepository.findLoginInfoByName(name);
		String re = "NO";
		String rs = "YES";

		if(loginEntities.isEmpty()) {
			System.out.println("该用户不存在！");
			return re;
			
		}
		else{
			System.out.println("该用户已经注册，正在为您跳转...");
			
			LoginInfo user= loginEntities.get(0);//获取列表第一个元素
		
			session.setAttribute("userId", user.getId());//去LoginInfo里面加一个get方法，返回id就行了
			

		
		  return rs;
		
		}
	}
	
	

}
