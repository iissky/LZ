package com.service;

import java.math.BigDecimal;

import org.springframework.aop.ThrowsAdvice;

import com.pojo.LzWeightset;
import com.pojo.PageBean;

public interface IWeightService {
	public PageBean<LzWeightset> findWeightPage(int pageIndex, int pageCount,String sql);
	
	/**
	 * 开放新的权重兑换轮次
	 * @param totallimit 全网上限
	 * @param ratio 兑换比例
	 * @param perlimit 个人上限
	 * @return
	 */
	public int addWeight(BigDecimal totallimit,BigDecimal ratio,BigDecimal perlimit);
	
	/**
	 * 查询当前有效权重兑换轮次信息
	 * @return
	 */
	public LzWeightset findCurrentWeight();
	
	/**
	 * 用户钱币兑换权重
	 */
	public void moneyToWeight(String phone,int weightSetId,BigDecimal money,BigDecimal weight) throws Exception;
	
	/**
	 * 转盘游戏充值权重
	 * @param phone
	 * @param weight
	 * @return 1.成功  0.失败
	 */
	public int dialWeightOrder(String phone,BigDecimal weight);
}
