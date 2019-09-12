package com.service;

import java.math.BigDecimal;

import org.springframework.transaction.annotation.Transactional;

public interface IRedPackageService {

	/**
	 * 
	 * @param userPhone
	 * @param type
	 * @param amount
	 * @param count
	 * @return 数组第一个数字为状态： 0.成功 1.用户无效  2.用户余额不足 3.红包类型无效
	 *         数组第二个数字为红包id
	 */
	int[] sendRedPackage(String userPhone, String type, BigDecimal amount, int count);

	
	String[] reviceRedPackage(String userPhone,String rpid);
}