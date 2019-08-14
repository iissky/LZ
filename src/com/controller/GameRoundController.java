package com.controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.pojo.LzGameround;
import com.pojo.LzUserinfo;
import com.pojo.PageBean;
import com.service.IGameRoundService;

@Controller
@RequestMapping("/game")
public class GameRoundController {
	@Autowired
	IGameRoundService gameSer;
	
	public void setGameSer(IGameRoundService gameSer) {
		this.gameSer = gameSer;
	}

	@RequestMapping("/gameListPage")
	public String gameListPage(Map model, String pageIndex){
		int index = 1;
		if(pageIndex!=null){
			index = Integer.valueOf(pageIndex);
		}
		PageBean<LzGameround> pb = gameSer.findGameRoundByPage(index, 5, "select * from lz_gameround where 1=1 order by createtime desc");
		model.put("pb", pb);
		return "gameRoundList";
	}
	@RequestMapping(value="/gameLimit")
	public String gameLimit(Map model){
		try {
			String path = this.getClass().getResource("/").getPath();
			InputStream in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			String gameLimit = p.getProperty("gameLimit");
			model.put("limit", gameLimit);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "gameLimit";
	}
	
	@RequestMapping("/gameLimitSet")
	public String gameLimitSet(Map model,String limit){
		try {
			String path = this.getClass().getResource("/").getPath();
			InputStream in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			in.close();
			FileOutputStream fot = new FileOutputStream(path+"/gameset.properties");
			p.setProperty("gameLimit", limit);
			String gameLimit = p.getProperty("gameLimit");
			model.put("limit", gameLimit);
			p.store(fot, "");
			fot.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "gameLimit";
	}
	
	@RequestMapping("/startGame")
	public String startGame(ModelMap model){
		gameSer.createGameRound();
		try {
			String path = this.getClass().getResource("/").getPath();
			InputStream in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			String gameLimit = p.getProperty("gameLimit");
			model.put("limit", gameLimit);
			in.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		model.addAttribute("mess", "<script>alert('启动成功')</script>");
		return "gameLimit";
	}
}
