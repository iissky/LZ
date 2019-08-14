package com.api;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.BaseJson;
import com.api.json.TotalMoneyAndWeightJson;
import com.dao.LzDealorderMapper;
import com.dao.LzUserinfoMapper;
import com.dao.LzWeightaccountMapper;
import com.pojo.LzDealorder;
import com.pojo.LzUserinfo;
import com.pojo.LzWeightaccount;

@Controller
public class OtherApi {
	@Autowired
	LzUserinfoMapper userMapper;
	@Autowired
	LzDealorderMapper orderMapper;
	@Autowired
	LzWeightaccountMapper weightAccountMapper;
	
	public void setUserMapper(LzUserinfoMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setOrderMapper(LzDealorderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}
	
	public void setWeightAccountMapper(LzWeightaccountMapper weightAccountMapper) {
		this.weightAccountMapper = weightAccountMapper;
	}

	/**
	 * 获取当前全网用户钱币数和权重数
	 * @return
	 */
	@RequestMapping("/getTotalMoenyAndWeight")
	public @ResponseBody TotalMoneyAndWeightJson getTotalMoenyAndWeight(){
		TotalMoneyAndWeightJson json = new TotalMoneyAndWeightJson();
		try {
			Map<String, BigDecimal> map = userMapper.getAllBalanceAndWeight();
			//获取全网当前有效用户总和
			BigDecimal totalBalance = map.get("totalBalance");
			BigDecimal totalWeight = map.get("totalWeight");
			
			json.setResultCode("1001");
			json.setResultMess("成功");
			json.setTotalMoney(totalBalance);
			json.setTotalWeight(totalWeight);
		} catch (Exception e) {
			e.printStackTrace();
			json.setResultCode("4001");
			json.setResultMess("失败");
		}
		return json;
	}
	/**
	 * 根据用户手机号查询用户详细信息
	 * @param userPhone
	 * @return
	 */
	@RequestMapping("/getUserinfoByPhone")
	public @ResponseBody LzUserinfo getUserinfoByPhone(String userPhone){
		List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where phone='"+userPhone+"'");
		BaseJson json = new BaseJson();
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return new LzUserinfo();
	}
	
	/**
	 * 用户钱币交易信息查询
	 * @param userPhone
	 * @return
	 */
	@RequestMapping("/getUserDealOrder")
	public @ResponseBody List<LzDealorder> getUserDealOrder(String userPhone){
		List<LzDealorder> list = orderMapper.selectBySql("select * from lz_dealorder where dealphone='"+userPhone+"' order by dealtime desc");
		return list;
	}
	
	/**
	 * 用户权重兑换信息查询
	 * @param userPhone
	 * @return
	 */
	@RequestMapping("/getUserWeightRecode")
	public @ResponseBody List<LzWeightaccount> getUserWeightRecode(String userPhone){
		List<LzWeightaccount> list = weightAccountMapper.selectBySql("select * from lz_weightaccount where phone='"+userPhone+"' order by dealtime desc");
		return list;
	}
}
