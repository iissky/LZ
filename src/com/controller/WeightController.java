package com.controller;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import com.pojo.LzUserinfo;
import com.pojo.LzWeightset;
import com.pojo.PageBean;
import com.service.IUserService;
import com.service.IWeightService;

@Controller
public class WeightController {
	@Autowired
	IWeightService weightSer;
	
	public void setWeightSer(IWeightService weightSer) {
		this.weightSer = weightSer;
	}

	@RequestMapping("/weightListPage")
	public String weightListPage(Map model,String pageIndex){
		int index = 1;
		if(pageIndex!=null){
			index = Integer.valueOf(pageIndex);
		}
		PageBean<LzWeightset> pb = weightSer.findWeightPage(index, 20, "select * from lz_weightSet where 1=1 order by createtime desc");
		model.put("pb",pb);
		return "weightList";
	}
	@RequestMapping("/addWeight")
	public void addWeight(BigDecimal totallimit,BigDecimal ratio,BigDecimal perlimit){
		System.out.println("ssss");
		weightSer.addWeight(totallimit, ratio, perlimit);
	}
}
