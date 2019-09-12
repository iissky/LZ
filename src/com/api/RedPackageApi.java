package com.api;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.BaseJson;
import com.dao.LzRedpackageMapper;
import com.dao.LzRedpackagedetailMapper;
import com.dao.LzUserinfoMapper;
import com.pojo.LzRedpackagedetail;
import com.service.IRedPackageService;

@Controller
@RequestMapping("/redpackage")
public class RedPackageApi {
	@Autowired
	IRedPackageService redSer;
	@Autowired
	LzRedpackagedetailMapper redDetailMapper;
	/**
	 * 发送红包
	 * @param userPhone 发送者手机号
	 * @param type 类型 1.单个  2.多个
	 * @param amount 总金额
	 * @param count 红包个数
	 * @return
	 */
	@RequestMapping("/sendRedPackage")
	public @ResponseBody Map<String, Object> sendRedPackage(String userPhone,String type,BigDecimal amount,int count){
		Map<String, Object> map = new HashMap<String, Object>();
		if(Double.valueOf(amount.toString())/count<0.01){
			map.put("resultCode", "4005");
			map.put("resultMess", "每个红包金额最小不能小于0.01");
			return map;
		}
		int num[] = redSer.sendRedPackage(userPhone, type, amount, count);
		if(num[0]==1){
			map.put("resultCode", "4001");
			map.put("resultMess", "用户无效");
			return map;
		}else if(num[0]==2){
			map.put("resultCode", "4002");
			map.put("resultMess", "用户余额不足");
			return map;
		}else if(num[0]==3){
			map.put("resultCode", "4003");
			map.put("resultMess", "红包类型无效");
			return map;
		}else if(num[0]==0){
			map.put("resultCode", "1001");
			map.put("resultMess", "成功");
			map.put("rpid", num[1]);
			return map;
		}else{
			map.put("resultCode", "4004");
			map.put("resultMess", "未知错误");
			return map;
		}
	}
	/**
	 * 接受红包
	 * @param userPhone
	 * @param rpid
	 * @return
	 */
	@RequestMapping("/receiveRedPackage")
	public @ResponseBody Map<String,Object> receiveRedPackage(String userPhone,String rpid){
		Map<String,Object> map = new HashMap<>();
		String strs[] = redSer.reviceRedPackage(userPhone, rpid);
		if("0".equals(strs[0])){
			map.put("resultCode", "4001");
			map.put("resultMess", "红包已经领完");
			return map;
		}else if("1".equals(strs[0])){
			map.put("resultCode", "1001");
			map.put("resultMess", "成功");
			map.put("amount", new BigDecimal(strs[1]));
			return map;
		}else if("2".equals(strs[0])){
			map.put("resultCode", "4002");
			map.put("resultMess", "该用户已经领取过该红包");
			return map;
		}
		map.put("resultCode", "4003");
		map.put("resultMess", "错误");
		return map;
	}
	
	@RequestMapping("/redPackageDetail")
	public @ResponseBody Map<String, Object> redPackageDetail(String rpid){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<LzRedpackagedetail> list = redDetailMapper.findBySql("select * from lz_redpackagedetail where rpid='"+rpid+"'");
			if(list!=null&&list.size()>0){
				map.put("redCount", list.size());
				
				for (Iterator iterator = list.iterator(); iterator.hasNext();) {
					LzRedpackagedetail detail = (LzRedpackagedetail) iterator.next();
					if("0".equals(detail.getStatus())){
						iterator.remove();
					}
				}
				map.put("receiveCount", list.size());
				map.put("tetailList", list);
				map.put("resultCode", "1001");
				map.put("resultMess", "成功");
				return map;
			}else{
				map.put("resultCode", "4001");
				map.put("resultMess", "红包id无效");
				return map;
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put("resultCode", "4002");
			map.put("resultMess", "未知错误");
			return map;
		}
	}
}
