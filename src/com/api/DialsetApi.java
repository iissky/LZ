package com.api;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.DialSetJson;
import com.dao.LzDialsetMapper;
import com.pojo.UserDialRecode;
import com.api.json.BaseJson;
import com.service.IActiveCodeService;
import com.service.IAuthCodeService;
import com.service.IMoneyService;
import com.service.IWeightService;

/**
 * 转盘抽奖相关接口
 * 
 * @author gy
 *
 */
@Controller
public class DialsetApi {
	@Autowired
	IActiveCodeService codeSer;
	@Autowired
	IWeightService weightSer;
	@Autowired
	IMoneyService moneySer;
	@Autowired
	LzDialsetMapper dialMapper;

	public void setMoneySer(IMoneyService moneySer) {
		this.moneySer = moneySer;
	}

	public void setWeightSer(IWeightService weightSer) {
		this.weightSer = weightSer;
	}

	public void setCodeSer(IActiveCodeService codeSer) {
		this.codeSer = codeSer;
	}

	public void setDialMapper(LzDialsetMapper dialMapper) {
		this.dialMapper = dialMapper;
	}

	/**
	 * 获取转盘游戏内容设置
	 * 
	 * @return
	 */
	@RequestMapping("/getDialset")
	public @ResponseBody DialSetJson getDialset() {
		DialSetJson dsj = new DialSetJson();
		try {
			String path = this.getClass().getResource("/").getPath();
			InputStream in = new FileInputStream(path + "/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			dsj.setContent1(p.getProperty("dialContent1"));
			dsj.setContent2(p.getProperty("dialContent2"));
			dsj.setContent3(p.getProperty("dialContent3"));
			dsj.setContent4(p.getProperty("dialContent4"));
			dsj.setContent5(p.getProperty("dialContent5"));
			dsj.setContent6(p.getProperty("dialContent6"));

			dsj.setCount1(p.getProperty("dialCount1"));
			dsj.setCount2(p.getProperty("dialCount2"));
			dsj.setCount3(p.getProperty("dialCount3"));
			dsj.setCount4(p.getProperty("dialCount4"));
			dsj.setCount5(p.getProperty("dialCount5"));
			dsj.setCount6(p.getProperty("dialCount6"));

			dsj.setOdds1(p.getProperty("dialOdds1"));
			dsj.setOdds2(p.getProperty("dialOdds2"));
			dsj.setOdds3(p.getProperty("dialOdds3"));
			dsj.setOdds4(p.getProperty("dialOdds4"));
			dsj.setOdds5(p.getProperty("dialOdds5"));
			dsj.setOdds6(p.getProperty("dialOdds6"));
			dsj.setResultCode("1001");
			dsj.setResultMess("成功");
		} catch (Exception e) {
			e.printStackTrace();
			dsj.setResultCode("4001");
			dsj.setResultMess("失败");
		}
		return dsj;
	}

	/**
	 * 验证激活码并使用
	 * 
	 * @param userPhone
	 * @param activeCode
	 * @return
	 */
	@RequestMapping("/activeCodeAuth")
	public @ResponseBody BaseJson activeCodeAuth(String userPhone, String activeCode) {
		BaseJson json = new BaseJson();
		String result = codeSer.activeCodeAuth(userPhone, activeCode);
		switch (result) {
		case "1":// 成功
			json.setResultCode("1001");
			json.setResultMess("成功");
			break;
		case "2":// 没有该激活码
			json.setResultCode("4002");
			json.setResultMess("没有该激活码");
			break;
		case "3":// 激活码已被使用
			json.setResultCode("4003");
			json.setResultMess("该激活码已被使用");
			break;
		}
		return json;
	}

	/**
	 * 转盘抽奖权重充值
	 * 
	 * @param userPhone
	 * @param weight
	 * @return
	 */
	@RequestMapping("/dialWeightOrder")
	public @ResponseBody BaseJson dialWeightOrder(String userPhone, BigDecimal weight) {
		BaseJson bj = new BaseJson();
		int num = weightSer.dialWeightOrder(userPhone, weight);
		if (num == 1) {// 成功
			bj.setResultCode("1001");
			bj.setResultMess("成功");
		} else {
			bj.setResultCode("4001");
			bj.setResultMess("失败");
		}
		return bj;
	}

	/**
	 * 转盘抽奖钱币充值
	 * 
	 * @param userPhone
	 * @param money
	 * @return
	 */
	@RequestMapping("/dialMoneyOrder")
	public @ResponseBody BaseJson dialMoneyOrder(String userPhone, BigDecimal money) {
		BaseJson bj = new BaseJson();
		int num = moneySer.dialMoneyOrder(userPhone, money);
		if (num == 1) {// 成功
			bj.setResultCode("1001");
			bj.setResultMess("成功");
		} else {
			bj.setResultCode("4001");
			bj.setResultMess("失败");
		}
		return bj;
	}

	/**
	 * 获取用户中奖纪录
	 * 
	 * @param userPhone
	 * @param money
	 * @return
	 */
	@RequestMapping("/getUserDialRecode")
	public @ResponseBody List<UserDialRecode> getUserDialRecode(String userPhone) {
		List<UserDialRecode> list = dialMapper.getUserDialRecode(userPhone);
		return list;
	}
}
