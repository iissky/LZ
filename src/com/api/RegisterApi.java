package com.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.api.json.AuthCodeJson;
import com.api.json.User;
import com.pojo.LzAuthcode;
import com.pojo.LzUserinfo;
import com.service.IAuthCodeService;
import com.service.IUserService;

import net.sf.json.JSONObject;
/**
 * 注册相关接口
 * @author gy
 *
 */
@Controller
public class RegisterApi {
	@Autowired
	IUserService userSer;
	@Autowired
	IAuthCodeService codeSer;
	@Autowired
	RestTemplate rest;
	
	public void setCodeSer(IAuthCodeService codeSer) {
		this.codeSer = codeSer;
	}

	public void setUserSer(IUserService userSer) {
		this.userSer = userSer;
	}

	/**
	 * 手机注册获取验证码
	 * @param userPhone
	 * @param authid
	 * @return
	 */
	@RequestMapping(value="/getAuthCode")
	public @ResponseBody AuthCodeJson getAuthCode(String userPhone,String authid,HttpServletRequest req){
		userPhone = req.getParameter("userPhone");
		authid = req.getParameter("authid");
		AuthCodeJson rj = new AuthCodeJson();
		
		LzUserinfo user =  userSer.findUserByPhone(userPhone);
		if(user!=null){//已经被注册
			rj.setResultCode("4003");
			rj.setResultMess("该手机号已被注册");
			return rj;
		}
		
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
	 * APP端完成注册
	 * @param userPhone
	 * @param authCode
	 * @param mobileId
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value="/registerFinish")
	public @ResponseBody AuthCodeJson registerFinish(String userPhone,String authCode,String mobileId,String pwd){
		AuthCodeJson rj = new AuthCodeJson();
		
		LzAuthcode la = codeSer.authCode(userPhone, authCode);
		if(la==null){//验证码错误或者超过三分钟
			rj.setResultCode("4002");
			rj.setResultMess("验证码错误或者超过三分钟");
			return rj;
		}
		LzUserinfo user = userSer.findUserByPhone(userPhone);
		if(user!=null){
			rj.setResultCode("4003");//手机号被占用
			rj.setResultMess("手机号被占用");
			return rj;
		}
		int count = userSer.countByMobileId(mobileId);
		if(count>=3){
			rj.setResultCode("4004");//该手机注册达三个上限
			rj.setResultMess("该手机注册达三个上限");
			return rj;
		}
		int num = userSer.register(userPhone, mobileId, pwd);
		if(num==1){
			rj.setResultCode("1001");//成功
			rj.setResultMess("成功");
			return rj;
		}
		rj.setResultCode("4001");//失败
		rj.setResultMess("失败");
		return rj;
	}
	
	/**
	 * 邀请完成注册
	 * @param userPhone 
	 * @param authCode 
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value="/inviteRegisterFinish")
	public @ResponseBody AuthCodeJson inviteRegisterFinish(String userPhone,String authCode,String pwd,String inviteUserCode){
		AuthCodeJson rj = new AuthCodeJson();
		
		LzAuthcode la = codeSer.authCode(userPhone, authCode);
		if(la==null){//验证码错误或者超过三分钟
			rj.setResultCode("4002");
			rj.setResultMess("验证码错误或者超过三分钟");
			return rj;
		}
		LzUserinfo user = userSer.findUserByPhone(userPhone);
		if(user!=null){
			rj.setResultCode("4003");//手机号被占用
			rj.setResultMess("手机号被占用");
			return rj;
		}
		
		//环信注册
		try {
			User user1 = new User();
			user1.setUsername(userPhone);
			user1.setPassword(userPhone);                            
		    Object obj = rest.postForEntity("http://a1.easemob.com/1113190815243420/lzapp/users", user1, Object.class);
			System.out.println(obj.toString());
		} catch (Exception e) {
			System.out.println("400错误==============="+e.getMessage());
			e.printStackTrace();
			rj.setResultCode("4004");//手机号被占用
			rj.setResultMess("环信注册失败");
			return rj;
		}
		
		int num = userSer.inviteRegister(userPhone, pwd, inviteUserCode);
		if(num==1){
			rj.setResultCode("1001");//成功
			rj.setResultMess("成功");
			return rj;
		}
		rj.setResultCode("4001");//失败
		rj.setResultMess("失败");
		return rj;
	}
}
