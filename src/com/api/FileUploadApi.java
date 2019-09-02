package com.api;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.api.json.BaseJson;
import com.pojo.LzUserinfo;
import com.service.IUserService;
@Controller
@RequestMapping("/file")
public class FileUploadApi {
	@Autowired
	IUserService userSer;
	@RequestMapping(value="/picupload",method=RequestMethod.POST)
	public @ResponseBody BaseJson upload(@RequestParam CommonsMultipartFile file,HttpServletRequest req,String userPhone){
		String path = req.getServletContext().getRealPath("/pic");
		
		BaseJson bj = new BaseJson();
		String picname = "";
		try {
			if(file.getSize()>1024*1024*2){
				bj.setResultCode("4001");
				bj.setResultMess("文件不能超过2M");
				return bj;
			}
			//文件后缀
			String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
			if("png".equalsIgnoreCase(suffix)||"jpg".equalsIgnoreCase(suffix)||"jpeg".equalsIgnoreCase(suffix)||"bmp".equalsIgnoreCase(suffix)){
				
			}else{
				bj.setResultCode("4002");
				bj.setResultMess("文件格式只能为jpg,png,jpeg,bmp");
				return bj;
			}
			picname = System.currentTimeMillis()+"."+suffix;
			File f = new File(path+"/"+picname);
			file.transferTo(f);
			LzUserinfo userinfo = userSer.findUserByPhone(userPhone);
			//如果有就删除原有的图片
			String oldpicpath = userinfo.getPicpath();
			if(oldpicpath!=null||!"".equals(oldpicpath)){
				File oldf = new File(path+"/"+oldpicpath);
				if(oldf.exists()){
					oldf.delete();
				}
			}
			
			userinfo.setPicpath(picname);
			userSer.modifierUser(userinfo);
		} catch (Exception e) {
			bj.setResultCode("4003");
			bj.setResultMess("未知错误，失败");
			e.printStackTrace();
			return bj;
		} 
		bj.setResultCode("1001");
		bj.setResultMess(picname);
		return bj;
	}
}
