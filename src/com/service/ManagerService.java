package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LzManagerMapper;
import com.pojo.LzManager;
@Service
public class ManagerService implements IManagerService {
	@Autowired
	LzManagerMapper lmDAO;
	
	public void setLmDAO(LzManagerMapper lmDAO) {
		this.lmDAO = lmDAO;
	}

	@Override
	public LzManager login(String username, String pwd) {
		return lmDAO.selectManagerBy(username, pwd);
	}
}
