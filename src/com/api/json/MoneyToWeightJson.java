package com.api.json;

import java.math.BigDecimal;

public class MoneyToWeightJson {
	String resultCode;
	String resultMess;
	int weightSetId;
	BigDecimal ratio;
	BigDecimal surplusLimit;
	BigDecimal balance;
	BigDecimal weight;
	
	
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
	public BigDecimal getRatio() {
		return ratio;
	}
	public void setRatio(BigDecimal ratio) {
		this.ratio = ratio;
	}
	public BigDecimal getSurplusLimit() {
		return surplusLimit;
	}
	public void setSurplusLimit(BigDecimal surplusLimit) {
		this.surplusLimit = surplusLimit;
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
	public int getWeightSetId() {
		return weightSetId;
	}
	public void setWeightSetId(int weightSetId) {
		this.weightSetId = weightSetId;
	}
	
}
