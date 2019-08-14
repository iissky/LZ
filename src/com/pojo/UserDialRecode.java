package com.pojo;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 用户转盘抽奖查询纪录
 * @author gy
 *
 */
public class UserDialRecode {
	String dealType;
	BigDecimal dealAmount;
	String dealTime;
	public String getDealType() {
		return dealType;
	}
	public void setDealType(String dealType) {
		this.dealType = dealType;
	}
	public BigDecimal getDealAmount() {
		return dealAmount;
	}
	public void setDealAmount(BigDecimal dealAmount) {
		this.dealAmount = dealAmount;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
	}
	
	
}
