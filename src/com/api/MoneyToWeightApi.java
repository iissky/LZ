package com.api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.MoneyToWeightJson;
import com.pojo.LzUserinfo;
import com.pojo.LzWeightset;
import com.service.IUserService;
import com.service.IWeightAccountService;
import com.service.IWeightService;
/**
 * 钱币换权重接口
 * @author gy
 *
 */
@Controller
public class MoneyToWeightApi {
	@Autowired
	IWeightAccountService waccountSer;
	@Autowired
	IUserService userSer;
	@Autowired
	IWeightService weightSer;
	
	public void setWaccountSer(IWeightAccountService waccountSer) {
		this.waccountSer = waccountSer;
	}

	public void setUserSer(IUserService userSer) {
		this.userSer = userSer;
	}

	public void setWeightSer(IWeightService weightSer) {
		this.weightSer = weightSer;
	}

	/**
	 * 获取当前有效兑换权重轮次信息
	 * @param userPhone
	 * @return
	 */
	@RequestMapping(value="/getWeightConvers")
	public @ResponseBody MoneyToWeightJson getWeightConvers(String userPhone){
		MoneyToWeightJson mwj = new MoneyToWeightJson();
		try{
			LzWeightset lw = weightSer.findCurrentWeight();
			if(lw==null){//当前没有开饭兑换轮次
				mwj.setResultCode("4002");
				mwj.setResultMess("当前没有开饭兑换轮次");
				return mwj;
			}else{
				//获取当前手机当前轮次总兑换权重总数
				BigDecimal sumWeight = waccountSer.getSumByPhoneAndWeightSetId(userPhone, lw.getWeiid());
				mwj.setWeightSetId(lw.getWeiid());
				mwj.setRatio(lw.getRatio());
				mwj.setSurplusLimit(lw.getPerlimit().subtract(sumWeight));
				mwj.setResultCode("1001");
				mwj.setResultMess("成功");
			}
		}catch (Exception e) {
			e.printStackTrace();
			mwj.setResultCode("4001");
			mwj.setResultMess("失败");
		}
		return mwj;
	}
	
	/**
	 * 用户钱币兑换权重
	 * @param userPhone
	 * @param money
	 * @param weight
	 * @return
	 */
	@RequestMapping(value="/moneyToWeight")
	public @ResponseBody MoneyToWeightJson moneyToWeight(String userPhone,BigDecimal money,BigDecimal weight,int weightSetId){
		MoneyToWeightJson mwj = new MoneyToWeightJson();
		if(money.signum()==-1||weight.signum()==-1){
			mwj.setResultCode("4004");
			mwj.setResultMess("钱币和权重不能为负数");
			return mwj;
		}
		try {
			//查询用户信息
			LzUserinfo user = userSer.findUserByPhone(userPhone);
			if(user==null){//手机号无效
				mwj.setResultCode("4002");
				mwj.setResultMess("手机号无效");
				return mwj;
			}
			if(user.getBalance().compareTo(money)==-1){//余额不足
				mwj.setResultCode("4003");
				mwj.setResultMess("余额不足");
				return mwj;
			}
			//检查兑换上限
			//获取当前手机当前轮次总兑换权重总数
//			BigDecimal sumWeight = waccountSer.getSumByPhoneAndWeightSetId(userPhone, weightSetId);
			
			weightSer.moneyToWeight(userPhone, weightSetId, money, weight);
			//交易后查询余额
			user = userSer.findUserByPhone(userPhone);
			mwj.setResultCode("1001");
			mwj.setResultMess("成功");
			mwj.setBalance(user.getBalance());
			mwj.setWeight(user.getWeight());
		} catch (Exception e) {
			e.printStackTrace();
			mwj.setResultCode("4001");
			mwj.setResultMess("失败");
		}
		return mwj;
	}
}
