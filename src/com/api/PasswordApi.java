package com.api;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.BaseJson;
import com.api.json.AuthCodeJson;
import com.pojo.LzAuthcode;
import com.pojo.LzUserinfo;
import com.service.IAuthCodeService;
import com.service.IUserService;

import net.sf.json.JSONObject;
/**
 * 修改密码接口
 * @author gy
 *
 */
@Controller
public class PasswordApi {
	@Autowired
	IUserService userSer;
	@Autowired
	IAuthCodeService codeSer;
	
	public void setCodeSer(IAuthCodeService codeSer) {
		this.codeSer = codeSer;
	}

	public void setUserSer(IUserService userSer) {
		this.userSer = userSer;
	}
	/**
	 * 忘记登录密码，获取手机验证码
	 * @param userPhone
	 * @return
	 */
	@RequestMapping("/passwordGetAuthCode")
	public @ResponseBody AuthCodeJson passwordGetAuthCode(String userPhone,String authid,HttpServletRequest req){
		AuthCodeJson rj = new AuthCodeJson();
		if(!"I0Vt194Iq5PB".equals(authid)){
			rj.setResultCode("4002");
			rj.setResultMess("authid无效");
			return rj;
		}
		String authcode = new Date().getTime()%1000000+"";
		
		CloseableHttpClient client = HttpClientBuilder.create().build();                                                        
		HttpGet get = new HttpGet("http://api.weimi.cc/2/sms/send.html?mob="+userPhone+"&uid=I0Vt194Iq5PB&pas=3vzz5jye&type=json&cid=y0dSVqn22Ta7&p1="+authcode);
		
		CloseableHttpResponse response = null;
		try {
			// 由客户端执行(发送)Get请求
			response = client.execute(get);
			// 从响应模型中获取响应实体
			HttpEntity responseEntity = response.getEntity();
			System.out.println("响应状态为:" + response.getStatusLine());
			if("HTTP/1.1 404 Not Found".equals(response.getStatusLine())){
				rj.setResultCode("4001");
				rj.setResultMess("手机短信请求失败");
				return rj;
			}
			if (responseEntity != null) {
				String data = EntityUtils.toString(responseEntity);
				JSONObject json = JSONObject.fromObject(data);
				Integer code = (Integer) json.get("code");
				if(code==0){
					//保存到数据库
					codeSer.saveCode(userPhone, authcode);
					rj.setResultCode("1001");
					rj.setResultMess("成功");
					rj.setAuthCode(authcode);
					return rj;
				}else{
					rj.setResultCode("4001");
					rj.setResultMess("失败");
					return rj;
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
		return rj;
	}
	
	/**
	 * 忘记登录密码后，修改密码。
	 * @return
	 */
	@RequestMapping("/modifierPassword")
	public @ResponseBody BaseJson modifierPassword(String userPhone,String authCode,String pwd){
		BaseJson bj = new BaseJson();
		LzAuthcode la = codeSer.authCode(userPhone, authCode);
		if(la==null){//验证码错误或者超过三分钟
			bj.setResultCode("4002");
			bj.setResultMess("验证码错误或者超过三分钟");
			return bj;
		}
		
		//修改密码
		int num = userSer.modifierPassword(userPhone, pwd);
		if(num>0){
			bj.setResultCode("1001");
			bj.setResultMess("成功");
			return bj;
		}else{
			bj.setResultCode("4001");
			bj.setResultMess("失败，手机号无效");
			return bj;
		}
	}
}
