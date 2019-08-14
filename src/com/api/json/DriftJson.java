package com.api.json;


import java.math.BigDecimal;

/**
 * 漂流瓶对象
 * @author gy
 *
 */
public class DriftJson {
	int moneyId;
	BigDecimal money;
	public int getMoneyId() {
		return moneyId;
	}
	public void setMoneyId(int moneyId) {
		this.moneyId = moneyId;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
}
