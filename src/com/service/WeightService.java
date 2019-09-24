package com.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LzDealorderMapper;
import com.dao.LzUserinfoMapper;
import com.dao.LzWeightaccountMapper;
import com.dao.LzWeightsetMapper;
import com.pojo.LzDealorder;
import com.pojo.LzUserinfo;
import com.pojo.LzWeightaccount;
import com.pojo.LzWeightset;
import com.pojo.PageBean;
@Service
public class WeightService implements IWeightService{
	@Autowired
	LzWeightsetMapper weightMapper;
	@Autowired
	LzUserinfoMapper userMapper;
	@Autowired
	LzWeightaccountMapper weightAccountMapper;
	@Autowired
	LzDealorderMapper orderMapper;
	
	public void setWeightAccountMapper(LzWeightaccountMapper weightAccountMapper) {
		this.weightAccountMapper = weightAccountMapper;
	}

	public void setUserMapper(LzUserinfoMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setWeightMapper(LzWeightsetMapper weightMapper) {
		this.weightMapper = weightMapper;
	}
	public void setOrderMapper(LzDealorderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	@Override
	public PageBean<LzWeightset> findWeightPage(int pageIndex, int pageCount,String sql) {
		int totalCount = weightMapper.selectCountBySql("select count(*) from ("+sql+") a");
		List<LzWeightset> list = weightMapper.selectBySql("select * from ("+sql+") a limit "+((pageIndex-1)*pageCount)+","+pageCount);
		PageBean<LzWeightset> pb = new PageBean<LzWeightset>();
		pb.setPageIndex(pageIndex);
		pb.setTotalCount(totalCount);
		pb.setPageCount(pageCount);
		pb.setList(list);
		pb.setTotalPage(totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount)+1);
		return pb;
	}

	@Override
	@Transactional
	public int addWeight(BigDecimal totallimit, BigDecimal ratio, BigDecimal perlimit) {
		weightMapper.updateWeightByStatus();//将正在开放的轮次结束
		LzWeightset lw = new LzWeightset();
		lw.setTotallimit(totallimit);
		lw.setRatio(ratio);
		lw.setPerlimit(perlimit);
		lw.setCreatetime(new Date());
		lw.setStatus("1");
		return weightMapper.insert(lw);
	}

	@Override
	public LzWeightset findCurrentWeight() {
		List<LzWeightset> list = weightMapper.selectBySql("select * from lz_weightset where status=1 order by createtime desc limit 0,1");
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void moneyToWeight(String phone, int weightSetId, BigDecimal money, BigDecimal weight) throws Exception {
		//个人余额扣款，权重增加
		userMapper.moneyToWeight(money, weight, phone);
		//权重记账
		LzWeightaccount account = new LzWeightaccount();
		account.setDealtime(new Date());
		account.setDealtype("1");
		account.setPhone(phone);
		account.setWeight(weight);
		account.setWeightsetid(weightSetId);
		weightAccountMapper.insert(account);
		
		//交易信息记账
		LzDealorder order = new LzDealorder();
		order.setDealtype("8");
		order.setDealamount(money);
		order.setDealphone(phone);
		order.setDealtime(new Date());
		orderMapper.insert(order);
	}

	@Override
	@Transactional
	public int dialWeightOrder(String phone, BigDecimal weight) {
		//修改用户权重余额
		int num = userMapper.dialWeightOrder(phone, weight);
		if(num>0){//修改成功
			//记账权重充值记录
			LzWeightaccount weightAccount = new LzWeightaccount();
			weightAccount.setDealtime(new Date());
			weightAccount.setDealtype("2");
			weightAccount.setPhone(phone);
			weightAccount.setWeight(weight);
			weightAccountMapper.insert(weightAccount);
			return 1;
		}
		return 0;
	}

	@Override
	public int changeWeightStatus(String weightid, String status) {
		return weightMapper.changeWeightStatus(weightid, status);
	}

}
