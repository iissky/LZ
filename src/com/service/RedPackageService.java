package com.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.BaseJson;
import com.dao.LzDealorderMapper;
import com.dao.LzRedpackageMapper;
import com.dao.LzRedpackagedetailMapper;
import com.dao.LzUserinfoMapper;
import com.pojo.LzDealorder;
import com.pojo.LzRedpackage;
import com.pojo.LzRedpackagedetail;
import com.pojo.LzUserinfo;

@Service
public class RedPackageService implements IRedPackageService {
	@Autowired
	LzUserinfoMapper userMapper;
	@Autowired
	LzRedpackageMapper redMapper;
	@Autowired
	LzRedpackagedetailMapper redDetailMapper;
	@Autowired
	LzDealorderMapper orderMapper;
	@Override
	@Transactional
	public int[] sendRedPackage(String userPhone,String type,BigDecimal amount,int count){
		int nums[] = new int[2];
		BaseJson bj = new BaseJson();
		//1.扣除用户余额
		LzUserinfo user = userMapper.selectUserByPhone(userPhone);
		if(user==null){
			nums[0]=1;
			return nums; 
		}
		if(user.getBalance().compareTo(amount)==-1){//余额不足
			nums[0]=2;
			return nums;
		}
		user.setBalance(user.getBalance().subtract(amount));
		userMapper.updateByPrimaryKeySelective(user);
		
		//2.添加红包记录
		if("1".equals(type)){//单个红包
			LzRedpackage red = new LzRedpackage();
			red.setType(type);
			red.setAmount(amount);
			red.setCount(1);
			red.setSendphone(userPhone);
			red.setSendtime(new Date());
			red.setStatus("1");
			redMapper.insert(red);
			//记录红包明细
			LzRedpackagedetail detail = new LzRedpackagedetail();
			detail.setAmount(amount);
			detail.setRpid(red.getPrid());
			detail.setStatus("0");
			redDetailMapper.insert(detail);
			//记录交易信息
			
			LzDealorder order = new LzDealorder();
			order.setDealamount(amount);
			order.setDealphone(userPhone);
			order.setDealtime(new Date());
			order.setDealtype("6");
			orderMapper.insert(order);
			nums[0]=0;
			nums[1]=red.getPrid();
			return nums;
		}else if("2".equals(type)){//多个红包
			LzRedpackage red = new LzRedpackage();
			red.setType(type);
			red.setAmount(amount);
			red.setCount(count);
			red.setSendphone(userPhone);
			red.setSendtime(new Date());
			red.setStatus("1");
			redMapper.insert(red);
			//将红包拆分成红包明细
			List<BigDecimal> list = randomRedeEnvelope(amount.multiply(new BigDecimal(100)).intValue(),count);
			for (BigDecimal big : list) {
				LzRedpackagedetail detail = new LzRedpackagedetail();
				detail.setAmount(big);
				detail.setRpid(red.getPrid());
				detail.setStatus("0");
				redDetailMapper.insert(detail);
			}
			
			//记录交易信息
			LzDealorder order = new LzDealorder();
			order.setDealamount(amount);
			order.setDealphone(userPhone);
			order.setDealtime(new Date());
			order.setDealtype("6");
			orderMapper.insert(order);
			nums[0]=0;
			nums[1]=red.getPrid();
			return nums;
		}else{
			nums[0]=3;
			return nums;
		}
	}
	
	@Test
	public void main() {
//        List<BigDecimal> integers = randomRedeEnvelope(4, 3);
//        System.out.println(integers);
		System.out.println(new BigDecimal(0.01).compareTo(new BigDecimal(0.01)));
       
    }
	/**
	 * 
	 * @param m 金额  金额*100传进来 以分为单位
	 * @param n 个数
	 * @return
	 */
	public List<BigDecimal> randomRedeEnvelope(int m,int n){
        Random random=new Random();
        List<BigDecimal> list=new ArrayList<BigDecimal>();
        int sumNum=n;
        for(int i=0;i<sumNum-1;i++){
            int num=m/n*2;
            //产生1-m/n*2-1的随机数
            int j = random.nextInt(num-1)+1;
            //第一次产生的红包数
            BigDecimal divide = new BigDecimal(j).divide(new BigDecimal(100));
            list.add(divide);
            m-=j;
            n--;
        }
        list.add(new BigDecimal(m).divide(new BigDecimal(100)));
        return list;
    }

	/**
	 * 接收红包
	 * @return 数组第一个记录状态：0:红包领完  1：成功领取   2：已经领过该红包
	 */ 
	@Override
	@Transactional
	public String[] reviceRedPackage(String userPhone, String rpid) {
		String[] strs = new String[2];
		List<LzRedpackagedetail> list = redDetailMapper.findBySql("select * from lz_redpackagedetail where rpid='"+rpid+"' and status='0'");
		if(list==null||list.size()<1){//红包已经抢完
			strs[0] = "0";
			return strs;
		}
		List<LzRedpackagedetail> list2 = redDetailMapper.findBySql("select * from lz_redpackagedetail where rpid='"+rpid+"' and receivephone='"+userPhone+"'");
		if(list2!=null&&list2.size()>0){//已经领取
			strs[0] = "2";
			return strs;
		}
		if(list!=null&&list.size()>0){
			if(list.size()==1){//为最后一个红包，抢完修改
				List<LzRedpackage> redlist = redMapper.findBySql("select * from lz_redpackage where prid='"+rpid+"'");
				if(redlist!=null&&redlist.size()>0){
					LzRedpackage red = redlist.get(0);
					red.setStatus("2");
					//修改红包状态为已经领完
					redMapper.updateByPrimaryKeySelective(red);
				}
			}
			//正常领红包
			LzRedpackagedetail detail = list.get(0);
			LzUserinfo user = userMapper.selectUserByPhone(userPhone);
			user.setBalance(user.getBalance().add(detail.getAmount()));
			userMapper.updateByPrimaryKeySelective(user);//修改用户余额
			detail.setReceivephone(userPhone);
			detail.setStatus("1");
			detail.setReceivetime(new Date());
			detail.setReceiveusercode(user.getUsercode());
			detail.setRecevienikename(user.getNickname());
			detail.setPicpath(user.getPicpath());
			redDetailMapper.updateByPrimaryKeySelective(detail);//修改红包明细状态
			strs[1] = detail.getAmount().toString();
			
			//记录交易信息
			LzDealorder order = new LzDealorder();
			order.setDealamount(detail.getAmount());
			order.setDealphone(userPhone);
			order.setDealtime(new Date());
			order.setDealtype("7");
			orderMapper.insert(order);
		}
		strs[0]="1";
		return strs;
	}
	
}
