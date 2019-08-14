package com.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LzUserinfoMapper;
import com.pojo.LzUserinfo;
import com.pojo.PageBean;
@Service
public class UserService implements IUserService{
	@Autowired
	LzUserinfoMapper userMapper;
	
	public void setUserMapper(LzUserinfoMapper userMapper) {
		this.userMapper = userMapper;
	}


	@Override
	public PageBean<LzUserinfo> findAllUserPage(int pageIndex, int pageCount,String sql) {
		int totalCount = userMapper.selectCountBySql("select count(*) from ("+sql+") a");
		List<LzUserinfo> list = userMapper.selectBySql("select * from ("+sql+") a limit "+((pageIndex-1)*pageCount)+","+pageCount);
		PageBean<LzUserinfo> pb = new PageBean<LzUserinfo>();
		pb.setPageIndex(pageIndex);
		pb.setTotalCount(totalCount);
		pb.setPageCount(pageCount);
		pb.setList(list);
		pb.setTotalPage(totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount)+1);
		return pb;
	}


	@Override
	public LzUserinfo findUserByPhone(String phone) {
		List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where phone='"+phone+"'");
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	public int countByMobileId(String mobileId) {
		return userMapper.countByMobileId(mobileId);
	}
	
	
	@Override
	public int register(String phone, String mobileId, String pwd) {
		LzUserinfo user = new LzUserinfo();
		user.setCreatetime(new Date());
		user.setPassword(DigestUtils.md5Hex(pwd));
		user.setPhone(phone);
		user.setBalance(new BigDecimal(0));
		user.setWeight(new BigDecimal(10));
		user.setStatus("1");
		return userMapper.insert(user);
	}


	@Override
	public LzUserinfo loginAuth(String phone, String pwd) {
		pwd = DigestUtils.md5Hex(pwd);
		return userMapper.findUserbyPhoneAndPwd(phone, pwd);
	}

	
	public int modifierPassword(String phone,String pwd){
		LzUserinfo user = findUserByPhone(phone);
		pwd = DigestUtils.md5Hex(pwd);
		user.setPassword(pwd);
		return userMapper.updateByPrimaryKeySelective(user);
	}
}
