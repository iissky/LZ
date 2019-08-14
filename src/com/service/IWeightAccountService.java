package com.service;

import java.math.BigDecimal;

public interface IWeightAccountService {
	/**
	 * 获取某轮次该用户已经兑换权重总数
	 * @param phone
	 * @param wsid
	 * @return
	 */
	public BigDecimal getSumByPhoneAndWeightSetId(String phone,int wsid);
}
