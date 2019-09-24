package com.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dao.LzNoticeMapper;
import com.pojo.LzNotice;

@Controller
public class NoticeApi {
	@Autowired
	LzNoticeMapper noticeMapper;
	
	@RequestMapping("/getNotice")
	public @ResponseBody Map<String, Object> getNotice(){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			List<LzNotice> list = noticeMapper.selectBySql("select * from lz_notice where status=1 order by createtime desc");
			map.put("resultCode", "1001");
			map.put("resultMess", "成功");
			map.put("list", list);
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			map.put("resultCode", "4001");
			map.put("resultMess", "失败");
			return map;
		}
		
	}
}
