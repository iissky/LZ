package com.api;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.GameRoundJson;
import com.api.json.BaseJson;
import com.pojo.LzGameround;
import com.service.IGameRoundService;
/**
 * 游戏相关接口
 * @author gy
 *
 */
@Controller
public class GameApi {
	@Autowired
	IGameRoundService grSer;
	
	public void setGrSer(IGameRoundService grSer) {
		this.grSer = grSer;
	}

	/**
	 * 获取游戏轮次信息
	 * @param userPhone
	 * @return
	 */
	@RequestMapping(value="/getGameRound")
	public @ResponseBody GameRoundJson getGameRound(String userPhone){
		GameRoundJson grj = new GameRoundJson();
		try {
			LzGameround lg = grSer.getValidGameRound();
			if(lg==null){
				grj.setResultCode("4002");
				grj.setResultMess("游戏轮次暂未开启");
				return grj;
			}else{
				grj = grSer.getUserGameRoundJson(userPhone, lg);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			grj.setResultCode("4001");
			grj.setResultMess("失败");
		}
		return grj;
	}
	/**
	 * 点击漂流瓶
		userPhone:手机号
		moneyid:游戏漂流瓶轮次
	 * @return
	 */
	@RequestMapping(value="/rechargeMoneyOrder")
	public @ResponseBody BaseJson rechargeMoneyOrder(String userPhone,Integer moneyId){
		BaseJson rmj = new BaseJson();
		try {
				if(moneyId==null){
					rmj.setResultCode("4001");
					rmj.setResultMess("失败,moneyid为空");
					return rmj;
				}
				if(grSer.rechargeMoney(userPhone,moneyId)==1){
					rmj.setResultCode("1001");
					rmj.setResultMess("成功");
				}else{
					rmj.setResultCode("4001");
					rmj.setResultMess("失败");
				}
		} catch (Exception e) {
			e.printStackTrace();
			rmj.setResultCode("4001");
			rmj.setResultMess("失败");
		}
		return rmj;
	}
}












