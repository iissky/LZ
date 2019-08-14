package com.api.json;

import java.math.BigDecimal;
import java.util.List;

public class LoginJson {
	String resultCode;
	String resultMess;
	String userPhone;
	BigDecimal balance;
	BigDecimal weight;
	List<String> activeCodeList;
	
	
	public String getResultMess() {
		return resultMess;
	}
	public void setResultMess(String resultMess) {
		this.resultMess = resultMess;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public List<String> getActiveCodeList() {
		return activeCodeList;
	}
	public void setActiveCodeList(List<String> activeCodeList) {
		this.activeCodeList = activeCodeList;
	}
	
	
}
