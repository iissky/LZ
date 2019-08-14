package com.controller;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.pojo.LzManager;
import com.service.IManagerService;

@Controller
public class LoginController {
	@Autowired
	IManagerService managerSer;
	
	public void setManagerSer(IManagerService managerSer) {
		this.managerSer = managerSer;
	}
	@RequestMapping("/managerLogin")
	public String login(String username,String pwd,HttpServletRequest req){
		LzManager lm = managerSer.login(username, pwd);
		if(lm!=null){
			req.getSession().setAttribute("user", lm);
			return "index"; 
		}
		return "redirect:login.jsp?mess=error";
	}
}
