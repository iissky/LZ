package com.schedul;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dao.LzMessageMapper;
import com.pojo.LzMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 金色财金数据抓取
 * @author gy
 *
 */
//@Component
public class JinseSchedul {
	@Autowired
	LzMessageMapper lmm;
	
	public void setLmm(LzMessageMapper lmm) {
		this.lmm = lmm;
	}
	@Scheduled(cron="0/15 * * * * ?")
	public void kuaixun(){
		System.out.println("金色快讯开始=======");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet("https://api.jinse.com/v4/live/list?limit=20&reading=false&source=web&sort=&flag=up");
		
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = client.execute(get);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if("HTTP/1.1 404 Not Found".equals(response.getStatusLine())){
				return ;
			}
			if (responseEntity != null) {
				String data = EntityUtils.toString(responseEntity);
				JSONObject json = JSONObject.fromObject(data);
				JSONArray jsonlist = json.getJSONArray("list");
				JSONArray lives = jsonlist.getJSONObject(0).getJSONArray("lives");
				for (int i = 0; i < lives.size(); i++) {
					JSONObject kx = lives.getJSONObject(i);
					String cardid = "js"+kx.get("id").toString();
					if(lmm.findMessByCardid(cardid)==null){//排除重复数据
						LzMessage lzm = new LzMessage();
						lzm.setCardid(cardid);
						lzm.setMesstype("2");
						String content = kx.get("content").toString();
						lzm.setMesstitle(content.substring(content.indexOf("【")+1, content.indexOf("】")));
						lzm.setMesscontext(content.substring(content.indexOf("】")+1));
						lzm.setCreatetime(new Date());
						lmm.insert(lzm);
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (client != null) {
					client.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("金色财金快讯结束=======");
	}

	
	@Scheduled(cron="0/15 * * * * ?")
	public void toutiao(){
		System.out.println("金色财金头条开始=======");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet("https://www.jinse.com/");
		
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = client.execute(get);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if("HTTP/1.1 404 Not Found".equals(response.getStatusLine())){
				return ;
			}
			if (responseEntity != null) {
				String data = EntityUtils.toString(responseEntity);
				toutiao1(data,client,response,responseEntity);
				toutiao2(data,client,response,responseEntity);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (client != null) {
					client.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("头条结束=======");
	}
	
	public void toutiao1(String data,CloseableHttpClient client,CloseableHttpResponse response,HttpEntity responseEntity) throws Exception{
		Pattern patt = Pattern.compile("https://www.jinse.com/news/blockchain/\\d{6}.html"); 
		Matcher match = patt.matcher(data);
		Set<String> set = new HashSet<>();
		while(match.find()){//初步去重复
			set.add(match.group());
		}
		for (String s : set) {
			//排除数据库重复数据
			String cardid = "js"+s.replace("https://www.jinse.com/news/blockchain/","").replace(".html","");
			if(lmm.findMessByCardid(cardid)==null){
				HttpGet get2 = new HttpGet(s);
				response = client.execute(get2);
				responseEntity = response.getEntity();
				if (responseEntity != null) {
					patt = Pattern.compile("<h2>.*</h2>"); 
					String content = EntityUtils.toString(responseEntity);
					match = patt.matcher(content);
					LzMessage mess = new LzMessage();
					mess.setMesstype("1");
					mess.setCardid(cardid);
					while(match.find()){
						mess.setMesstitle(match.group().replaceAll("<h2>","").replaceAll("</h2>",""));
						patt = Pattern.compile("<div class=\"js-article-detail\">[\\s\\S]*</p></div>");
						match = patt.matcher(content);
						while(match.find()){
							mess.setMesscontext(match.group(0));
							mess.setCreatetime(new Date());
							lmm.insert(mess);
						}
					}
				}
			}
		}
	}
	
	
	public void toutiao2(String data,CloseableHttpClient client,CloseableHttpResponse response,HttpEntity responseEntity) throws Exception{
		Pattern patt = Pattern.compile("https://www.jinse.com/blockchain/\\d{6}.html"); 
		Matcher match = patt.matcher(data);
		Set<String> set = new HashSet<>();
		while(match.find()){//初步去重复
			set.add(match.group());
		}
		for (String s : set) {
			//排除数据库重复数据
			String cardid = "js"+s.replace("https://www.jinse.com/blockchain/","").replace(".html","");
			if(lmm.findMessByCardid(cardid)==null){
				HttpGet get2 = new HttpGet(s);
				response = client.execute(get2);
				responseEntity = response.getEntity();
				if (responseEntity != null) {
					patt = Pattern.compile("<h2>.*</h2>"); 
					String content = EntityUtils.toString(responseEntity);
					match = patt.matcher(content);
					LzMessage mess = new LzMessage();
					mess.setMesstype("1");
					mess.setCardid(cardid);
					while(match.find()){
						mess.setMesstitle(match.group().replaceAll("<h2>","").replaceAll("</h2>",""));
						patt = Pattern.compile("<div class=\"js-article-detail\">[\\s\\S]*</p></div>");
						match = patt.matcher(content);
						while(match.find()){
							mess.setMesscontext(match.group(0));
							mess.setCreatetime(new Date());
							lmm.insert(mess);
						}
					}
				}
			}
		}
	}
	@Test
	public void test(){
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet("https://www.jinse.com/");
		
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = client.execute(get);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			if("HTTP/1.1 404 Not Found".equals(response.getStatusLine())){
				return ;
			}
			if (responseEntity != null) {
				String data = EntityUtils.toString(responseEntity);
				Pattern patt = Pattern.compile("https://www.jinse.com/news/blockchain/\\d{6}.html"); 
				Matcher match = patt.matcher(data);
				Set<String> set = new HashSet<>();
				while(match.find()){//初步去重复
					set.add(match.group());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// 释放资源
				if (client != null) {
					client.close();
				}
				if (response != null) {
					response.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
