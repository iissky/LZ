package com.utils;

import java.beans.Beans;
import java.beans.Encoder;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dao.LzManagerMapper;
import com.dao.LzMessageMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.pojo.LzMessage;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class HttpClientTest {
	public static void main(String[] args) {
		
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpGet get = new HttpGet("https://api.jinse.com/v4/live/list?limit=20&reading=false&source=web&sort=&flag=up");
		
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = client.execute(get);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if("HTTP/1.1 404 Not Found".equals(response.getStatusLine())){
				return ;
			}
			if (responseEntity != null) {
				String data = EntityUtils.toString(responseEntity);
				System.out.println("响应内容为:" + data);
				JSONObject json = JSONObject.fromObject(data);
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
	@Test
	public void test(){
			 //金色头条
		      // 按指定模式在字符串查找
		      String line = "<div class=\"js-article-detail\">\r<p><strong>金色财经 比特币6月27日讯</strong> 最近这段时间，最引人关注的话题就是比特币价格再次暴涨，许多加密行业分析师和市场研究人员都预计比特币很快就会攀升到2017年的历史最高点，甚至会创造新的价格记录。虽然有人认为近期的比特币价格大涨可能是Facebook发行加密货币Libra导致的，但根据最新的一项分析显示，比特币价格激增的真正原因可能是机构投资者入局、以及市场基本面正在变得越来越成熟。</p><p><img src=\"https://img.jinse.com/1998529_image3.png\" title=\"LxM1Sh5siKgMlYjIBoIt2HQYXQIxVcJfwfc56z5q.jpeg\" alt=\"LxM1Sh5siKgMlYjIBoIt2HQYXQIxVcJfwfc56z5q.jpeg\"/></p><p>E投睿高级市场分析师马蒂·格林斯潘（Mati Greenspan）最近在推特上发布了一份数据分析，结果显示很多熟悉加密市场的交易者参与率正在大幅增加，包括Tether、美元、欧元、日元、人民币和英镑在内的传统货币资金都在流入比特币。6月26日，全球最大的加密货币交易所币安上的比特币价格突破了13000美元大关，因此你会发现传统货币流向比特币并不令人感到惊讶。</p><p>事实上，流入到比特币的大部分资金都是Tether，还有一些Tether资金作为BTC/ETH交易对流入到了以太坊生态系统。此外，Tether资金还推动了莱特币、瑞波币、比特币现金、以及ZCash/波场/Cardano等一些其他山寨币的流动性。</p><p>除了比特币，以太坊是Tether资金流入第二大的加密货币，部分交易者在购买了以太坊之后还会将其转换为EOS和波场等加密资产。此外，还有一些Tether资金被分配到了其他山寨币，其中受益最大的是瑞波币。</p><p>Tether之后，资金流动主要以美元形式出现，而且和预期的一样，美元流向最大的依然也是比特币，其次是以太坊、比特币现金和莱特币。</p><p><img src=\"https://img.jinse.com/1998531_image3.png\" title=\"vyGNHV3KN1fufAGYjsJFppQtghOEdsjiWXZaJ3DK.jpeg\" alt=\"vyGNHV3KN1fufAGYjsJFppQtghOEdsjiWXZaJ3DK.jpeg\"/></p><p>虽然欧元和日元也参与了不少加密货币投资，但在法定货币的加密货币流入方面，美元的主导地位是显而易见的。在加密货币投资中，另一个引人关注的法定货币趋势是欧元、日元和英镑等法定货币似乎更“喜欢”比特币。相比之下，人民币的加密货币投资更多元化，除了比特币之外，一些人民币还流入到了市值第三大的加密货币瑞波币。<br/></p><p>文章翻译自Ambcrypto</p></div><div>hrhgfhfghgfhhghfghfg</div>";
		      String pattern = "<div class=\"js-article-detail\">([\\S\\s]*)</p></div>";
		 
		      // 创建 Pattern 对象
		      Pattern r = Pattern.compile(pattern);
		 
		      // 现在创建 matcher 对象
		      Matcher m = r.matcher(line);
		      if (m.find( )) {
		         System.out.println("Found value: " + m.group(0) );
		      } else {
		         System.out.println("NO MATCH");
		      }
	}
	@Test
	public void test2(){
		//金色快讯
		String line="<div class=\"content\"><a href=\"/lives/104469.htm\" target=\"_blank\" class=\"livesb\">声音 | CFTC专员：继续看好隐私币的零知识证明技术</a> <a href=\"/lives/104469.htm\" target=\"_blank\">据AMBCrypto消息，近日，美国商品期货交易委员会（CFTC）专员Brian Quintenz接受采访时表示，尽管存在AML（反洗钱）的问题，但对零知识证明协议持积极态度。政府接受任何形式的隐私币的可能性很小，加密货币交易平台也禁止用户进行不受监控的交易，这也是为什么Monero没有在Coinbase上线的原因。</a> <!----> <!----></div>asdushdkasjhd</div>";
		 String pattern = "<div class=\"content\">[\\s\\S]*?</div>";
		 
	      // 创建 Pattern 对象
	      Pattern r = Pattern.compile(pattern);
	 
	      // 现在创建 matcher 对象
	      Matcher m = r.matcher(line);
	      if (m.find( )) {
	         System.out.println("Found value: " + m.group(0) );
	      } else {
	         System.out.println("NO MATCH");
	      }
	}
	@Test
	public void test3() throws Exception{
		String s = "\u3010\u52a8\u6001 | STP\u63d0\u51fa\u521b\u65b0PoB\u6a21\u578b\uff0c\u5ba3\u5e03\u56de\u8d2d100\u4e07\u7f8e\u91d1STPT\u5e76\u9501\u4ed3\u3011STP\u63d0\u51fa\u521b\u65b0Staking\u6a21";
		System.out.println(URLDecoder.decode(s,"utf-8"));
	}
	/**
	 * 手机短信测试
	 */
	@Test
	public void test4(){ 
		CloseableHttpClient client = HttpClientBuilder.create().build();                                                        
		HttpGet get = new HttpGet("http://api.weimi.cc/2/sms/send.html?mob=15274953596&uid=I0Vt194Iq5PB&pas=3vzz5jye&type=json&cid=y0dSVqn22Ta7&p1=123456");
		
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = client.execute(get);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if("HTTP/1.1 404 Not Found".equals(response.getStatusLine())){
				return ;
			}
			if (responseEntity != null) {
				String data = EntityUtils.toString(responseEntity);
				System.out.println("响应内容为:" + data);
				JSONObject json = JSONObject.fromObject(data);
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
