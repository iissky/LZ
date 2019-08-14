package com.service;

import java.math.BigDecimal;

public interface IMoneyService {
	/**
	 * 转盘抽奖钱币充值
	 * @param phone
	 * @param money
	 * @return
	 */
	public int dialMoneyOrder(String phone,BigDecimal money);
}
