package com.schedul;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.service.IGameRoundService;

@Component
public class GameRoundSchedul {
	@Autowired
	IGameRoundService grSer;
	
	public void setGrSer(IGameRoundService grSer) {
		this.grSer = grSer;
	}

//	@Scheduled(cron="0 */18 * * * ?")
	@Scheduled(fixedRate=1000*60*18)
//	@Scheduled(cron="0/15 * * * * ?")
	public void gameRound(){
		System.out.println("执行游戏轮次"+new Date());
		grSer.createGameRound();
	}
}
