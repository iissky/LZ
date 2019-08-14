package com.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LzAuthcodeMapper;
import com.pojo.LzActivecode;
import com.pojo.LzAuthcode;
@Service
public class AuthCodeService implements IAuthCodeService{
	@Autowired
	LzAuthcodeMapper codeMapper;
	
	public void setCodeMapper(LzAuthcodeMapper codeMapper) {
		this.codeMapper = codeMapper;
	}

	@Override
	public int saveCode(String phone, String code) {
		LzAuthcode authcode = new LzAuthcode();
		authcode.setAuthcode(code);
		authcode.setCreatetime(new Date());
		authcode.setPhone(phone);
		return codeMapper.insert(authcode);
	}

	@Override
	public LzAuthcode authCode(String phone, String code) {
		LzAuthcode la = codeMapper.findAuthcodeTothree(phone, code);
		return la;
	}

}
