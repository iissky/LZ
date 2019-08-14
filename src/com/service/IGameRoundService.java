package com.service;

import java.math.BigDecimal;

import com.api.json.GameRoundJson;
import com.pojo.LzGameround;
import com.pojo.PageBean;

public interface IGameRoundService {
	/**
	 * 查看游戏轮次信息，分页
	 * @param pageIndex
	 * @param pageCount
	 * @param sql
	 * @return
	 */
	public PageBean<LzGameround> findGameRoundByPage(int pageIndex,int pageCount,String sql); 
	
	/**
	 * 自动产生游戏轮次
	 */
	public void createGameRound();
	
	/**
	 * 获取当前有效游戏轮次
	 * @return
	 */
	public LzGameround getValidGameRound();
	
	/**
	 * 获取当前用户本轮次游戏信息对象
	 * @return
	 */
	public GameRoundJson getUserGameRoundJson(String phone,LzGameround lg);
	
	/**
	 * 游戏收集漂流瓶充值钱币
	 * @param moneyid
	 * @return
	 */
	public int rechargeMoney(String userPhone,int moneyid);
	
}
