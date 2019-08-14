package com.service;

import com.pojo.LzAuthcode;

public interface IAuthCodeService {
	public int saveCode(String phone,String code);
	
	/**
	 * 验证三分钟之内验证码是否正确
	 * @param phone
	 * @param code
	 * @return
	 */
	public LzAuthcode authCode(String phone,String code);
}
