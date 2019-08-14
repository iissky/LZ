package com.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/**
 * 登录请求接口
 * @author gy
 *
 */
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.LoginJson;
import com.pojo.LzActivecode;
import com.pojo.LzUserinfo;
import com.service.IActiveCodeService;
import com.service.IUserService;
/**
 * 登录验证接口
 * @author gy
 *
 */
@Controller
public class LoginApi {
	@Autowired
	IUserService userSer;
	@Autowired
	IActiveCodeService codeSer;
	
	public void setCodeSer(IActiveCodeService codeSer) {
		this.codeSer = codeSer;
	}

	public void setUserSer(IUserService userSer) {
		this.userSer = userSer;
	}

	/**
	 * 验证登录
	 * @param userPhone
	 * @param userPwd
	 * @return
	 */
	@RequestMapping(value="/loginAuth")
	public @ResponseBody LoginJson login(String userPhone,String userPwd){
		LoginJson lj = new LoginJson();
		LzUserinfo user = userSer.loginAuth(userPhone, userPwd);
		if(user==null){//用户名密码错误
			lj.setResultCode("4002");
			lj.setResultMess("用户名密码错误");
			return lj;
		}
		if("2".equals(user.getStatus())){//用户状态异常，冻结
			lj.setResultCode("4003");
			lj.setResultMess("用户状态异常，冻结");
			return lj;
		}
		List<LzActivecode> list = codeSer.findActiveCodeByPhone(userPhone);
		if(list!=null&&list.size()>0){
			List<String> activeCodeList = new ArrayList<>();
			for (LzActivecode code : list) {
				activeCodeList.add(code.getActivecode());
			}
			lj.setActiveCodeList(activeCodeList);
		}
		
		lj.setUserPhone(userPhone);
		lj.setBalance(user.getBalance());
		lj.setWeight(user.getWeight());
		lj.setResultCode("1001");
		lj.setResultMess("成功");
		return lj;
	}
}
