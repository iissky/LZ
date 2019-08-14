package com.api.json;

import java.math.BigDecimal;
import java.util.List;

public class GameRoundJson {
	String resultCode;
	String resultMess;
	int gameId;
	List<DriftJson> moneys;
	BigDecimal userMoney;
	BigDecimal userWeight;
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMess() {
		return resultMess;
	}
	public void setResultMess(String resultMess) {
		this.resultMess = resultMess;
	}
	public int getGameId() {
		return gameId;
	}
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	public List<DriftJson> getMoneys() {
		return moneys;
	}
	public void setMoneys(List<DriftJson> moneys) {
		this.moneys = moneys;
	}
	public BigDecimal getUserMoney() {
		return userMoney;
	}
	public void setUserMoney(BigDecimal userMoney) {
		this.userMoney = userMoney;
	}
	public BigDecimal getUserWeight() {
		return userWeight;
	}
	public void setUserWeight(BigDecimal userWeight) {
		this.userWeight = userWeight;
	}
}
