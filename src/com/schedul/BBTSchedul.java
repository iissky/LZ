package com.schedul;

import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dao.LzMessageMapper;
import com.pojo.LzMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * 巴比特数据抓取
 * @author gy
 *
 */
//@Component
public class BBTSchedul {
	@Autowired
	LzMessageMapper lmm;
	
	public void setLmm(LzMessageMapper lmm) {
		this.lmm = lmm;
	}
	
	@Scheduled(cron="0/15 * * * * ?")
	public void kuaixun(){
		System.out.println("巴比特快讯开始=======");
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet("https://app.blockmeta.com/w1/news/list?num=20&cat_id=4481&page=1");
		
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
				for (int i = 0; i < jsonlist.size(); i++) {
					JSONObject kx = jsonlist.getJSONObject(i);
					String cardid = "bbt"+kx.get("id").toString();
					if(lmm.findMessByCardid(cardid)==null){//排除重复数据
						LzMessage lzm = new LzMessage();
						lzm.setCardid(cardid);
						lzm.setMesstype("2");
						lzm.setMesstitle(kx.get("title").toString());
						lzm.setMesscontext(kx.get("content").toString());
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
		System.out.println("巴比特快讯结束=======");
	}
}
