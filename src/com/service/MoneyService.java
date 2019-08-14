package com.service;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LzAccountMapper;
import com.dao.LzDealorderMapper;
import com.dao.LzUserinfoMapper;
import com.pojo.LzAccount;
import com.pojo.LzDealorder;

@Service
public class MoneyService implements IMoneyService {
	@Autowired
	LzUserinfoMapper userMapper;
	@Autowired
	LzDealorderMapper orderMapper;
	@Autowired
	LzAccountMapper accountMapper;

	public void setUserMapper(LzUserinfoMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setOrderMapper(LzDealorderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	public void setAccountMapper(LzAccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	@Override
	@Transactional
	public int dialMoneyOrder(String phone, BigDecimal money) {
		// 修改用户钱币余额
		int num = userMapper.dialMoneyOrder(phone, money);
		if(num>0){
			// 记录交易信息
			LzDealorder order = new LzDealorder();
			order.setDealamount(money);
			order.setDealphone(phone);
			order.setDealtime(new Date());
			order.setDealtype("3");
			int orderid = orderMapper.insert(order);
			// 记账
			LzAccount account = new LzAccount();
			account.setAccounttime(new Date());
			account.setDealid(orderid);
			account.setAccountphone(phone);
			account.setAccounttype("1");
			account.setAmount(money);
			return accountMapper.insert(account);
		}
		return 0;
	}

}
