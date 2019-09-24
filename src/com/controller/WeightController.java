package com.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

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
	@RequestMapping("/changeWeightStatus")
	public String changeWeightStatus(String status,String weightid){
		weightSer.changeWeightStatus(weightid, status);
		return "redirect:weightListPage";
	}
	@RequestMapping("/weightConvertMess")
	public String weightConvertMess(Map model){
		String path = this.getClass().getResource("/").getPath();
		InputStream in;
		try {
			in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			model.put("weightMess", p.getProperty("weightMess"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "weightMess";
	}
	
	@RequestMapping("/setWeightConvertMess")
	public String setWeightConvertMess(String mess){
		String path = this.getClass().getResource("/").getPath();
		InputStream in;
		try {
			in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			
			FileOutputStream out = new FileOutputStream(path+"/gameset.properties");
			p.setProperty("weightMess", mess);
			
			p.store(out, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:weightConvertMess";
	}
}
