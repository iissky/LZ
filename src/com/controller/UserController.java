package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.pojo.LzUserinfo;
import com.pojo.PageBean;
import com.service.IUserService;

@Controller
public class UserController {
	@Autowired
	IUserService userSer;
	
	public void setUserSer(IUserService userSer) {
		this.userSer = userSer;
	}

	@RequestMapping("/userListPage")
	public ModelAndView userListPage(String pageIndex,String userPhone){
		ModelAndView mav = new ModelAndView();
		int index = 1;
		if(pageIndex!=null){
			index = Integer.valueOf(pageIndex);
		}
		String sql = "select * from lz_userinfo where 1=1";
		if(userPhone!=null&&!"".equals(userPhone)){
			sql = "select * from lz_userinfo where 1=1 and phone='"+userPhone+"'";
		}
		PageBean<LzUserinfo> pb = userSer.findAllUserPage(index, 20, sql);
		mav.addObject("pb", pb);
		mav.setViewName("userList");
		return mav;
	}
}
