package com.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.LzActivecode;
import com.pojo.PageBean;
import com.service.IActiveCodeService;

@Controller
public class ActiveCodeController {
	@Autowired
	IActiveCodeService activeCodeSer;
	
	public void setActiveCodeSer(IActiveCodeService activeCodeSer) {
		this.activeCodeSer = activeCodeSer;
	}

	@RequestMapping("/activeCodeList")
	public String activeCodeListPage(Map model,String pageIndex){
		int index = 1;
		if(pageIndex!=null){
			index = Integer.valueOf(pageIndex);
		}
		PageBean<LzActivecode> pb = activeCodeSer.findActiveCodePage(index, 20, "select * from lz_activecode where 1=1 order by createtime desc");
		model.put("pb", pb);
		return "activeCodeList";
	}
	@RequestMapping("/addActiveCode")
	public @ResponseBody String addActiveCode(String activeCode,String bindPhone){
		activeCodeSer.createNewActiveCode(activeCode, bindPhone);
		return "success";
	}
}
