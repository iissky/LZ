package com.service;

import java.util.List;

import com.pojo.LzUserinfo;
import com.pojo.PageBean;

public interface IUserService {
	public PageBean<LzUserinfo> findAllUserPage(int pageIndex,int pageCount,String sql);
	
	public LzUserinfo findUserByPhone(String phone);
	
	public int countByMobileId(String mobileId);
	
	public int register(String phone,String mobileId,String pwd);
	
	public LzUserinfo loginAuth(String phone,String pwd);
	
	public int modifierPassword(String phone,String pwd);
}
