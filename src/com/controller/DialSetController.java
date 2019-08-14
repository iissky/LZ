package com.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pojo.LzDialset;

@Controller
public class DialSetController {
	@RequestMapping("/dialShow")
	public String dialShow(Map model,String mess){
		try{
			String path = this.getClass().getResource("/").getPath();
			InputStream in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			LzDialset ld = new LzDialset();
			ld.setContent1(p.getProperty("dialContent1"));
			ld.setContent2(p.getProperty("dialContent2"));
			ld.setContent3(p.getProperty("dialContent3"));
			ld.setContent4(p.getProperty("dialContent4"));
			ld.setContent5(p.getProperty("dialContent5"));
			ld.setContent6(p.getProperty("dialContent6"));
			
			ld.setCount1(new BigDecimal(p.getProperty("dialCount1")));
			ld.setCount2(new BigDecimal(p.getProperty("dialCount2")));
			ld.setCount3(new BigDecimal(p.getProperty("dialCount3")));
			ld.setCount4(new BigDecimal(p.getProperty("dialCount4")));
			ld.setCount5(new BigDecimal(p.getProperty("dialCount5")));
			ld.setCount6(new BigDecimal(p.getProperty("dialCount6")));
			
			ld.setOdds1(new BigDecimal(p.getProperty("dialOdds1")));
			ld.setOdds2(new BigDecimal(p.getProperty("dialOdds2")));
			ld.setOdds3(new BigDecimal(p.getProperty("dialOdds3")));
			ld.setOdds4(new BigDecimal(p.getProperty("dialOdds4")));
			ld.setOdds5(new BigDecimal(p.getProperty("dialOdds5")));
			ld.setOdds6(new BigDecimal(p.getProperty("dialOdds6")));
			
			model.put("ld", ld);
			if(mess!=null){
				model.put("mess", "<script>alert('修改成功')</script>");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "dialSet";
	}
	
	@RequestMapping("/dialSetUpdate")
	public String dialSetUpdate(LzDialset ld){
		String path = this.getClass().getResource("/").getPath();
		try{
			InputStream in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			
			FileOutputStream out = new FileOutputStream(path+"/gameset.properties");
			p.setProperty("dialContent1", ld.getContent1());
			p.setProperty("dialContent2", ld.getContent2());
			p.setProperty("dialContent3", ld.getContent3());
			p.setProperty("dialContent4", ld.getContent4());
			p.setProperty("dialContent5", ld.getContent5());
			p.setProperty("dialContent6", ld.getContent6());
			
			p.setProperty("dialCount1", ld.getCount1().toString());
			p.setProperty("dialCount2", ld.getCount2().toString());
			p.setProperty("dialCount3", ld.getCount3().toString());
			p.setProperty("dialCount4", ld.getCount4().toString());
			p.setProperty("dialCount5", ld.getCount5().toString());
			p.setProperty("dialCount6", ld.getCount6().toString());
			
			p.setProperty("dialOdds1", ld.getOdds1().toString());
			p.setProperty("dialOdds2", ld.getOdds2().toString());
			p.setProperty("dialOdds3", ld.getOdds3().toString());
			p.setProperty("dialOdds4", ld.getOdds4().toString());
			p.setProperty("dialOdds5", ld.getOdds5().toString());
			p.setProperty("dialOdds6", ld.getOdds6().toString());
			
			p.store(out, "");
			out.close();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:dialShow?mess=success";
	}
}
