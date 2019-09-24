package com.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.LzNotice;
import com.pojo.PageBean;
import com.service.INoticeService;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	@Autowired
	INoticeService noticeSer;
	@RequestMapping("/noticeList")
	public ModelAndView noticeList(String pageIndex){
		ModelAndView mav = new ModelAndView();
		int index = 1;
		if(pageIndex!=null){
			index = Integer.valueOf(pageIndex);
		}
		String sql = "select * from lz_notice order by createtime desc";
		PageBean<LzNotice> pb = noticeSer.findAllNoticePage(index, 10, sql);
		mav.addObject("pb", pb);
		mav.setViewName("noticeList");
		return mav;
	}
	
	@RequestMapping("/addNotice")
	public String addNotice(String title,String content,String status){
		noticeSer.addNotice(title, content, status);
		return "redirect:notice/noticeList";
	}
	
	@RequestMapping("/toUpdateNotice")
	public String toUpdateNotice(String noticeid,Map model){
		LzNotice n = noticeSer.findNoticeById(noticeid);
		model.put("n", n);
		return "updateNotice";
	}
	@RequestMapping("/updateNotice")
	public String updateNotice(LzNotice n){
		n.setCreatetime(new Date());
		noticeSer.updateNotice(n);
		return "redirect:notice/noticeList";
	}
}
