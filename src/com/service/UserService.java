package com.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.LzActivecodeMapper;
import com.dao.LzUserinfoMapper;
import com.pojo.LzActivecode;
import com.pojo.LzUserinfo;
import com.pojo.PageBean;
@Service
public class UserService implements IUserService{
	@Autowired
	LzActivecodeMapper activeMapper;
	@Autowired
	LzUserinfoMapper userMapper;
	
	public void setUserMapper(LzUserinfoMapper userMapper) {
		this.userMapper = userMapper;
	}


	@Override
	/**
	 * 分页查询用户信息
	 */
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
	/**
	 * 通过手机号查找用户
	 */
	public LzUserinfo findUserByPhone(String phone) {
		List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where phone='"+phone+"'");
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}


	@Override
	/**
	 * 查询该手机唯一码注册了多少个账号
	 */
	public int countByMobileId(String mobileId) {
		return userMapper.countByMobileId(mobileId);
	}
	
	
	@Override
	/**
	 * 用户注册
	 */
	public int register(String phone, String mobileId, String pwd) {
		LzUserinfo user = new LzUserinfo();
		Random r = new Random();
		int num = r.nextInt(2146483647)+1000000;
		List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where usercode='"+num+"'");
		if(list!=null&&list.size()>0){//用户编号重复
			num = r.nextInt(2146483647)+1000000;
			list = userMapper.selectBySql("select * from lz_userinfo where usercode='"+num+"'");
		}
		
		user.setUsercode(num+"");
		user.setCreatetime(new Date());
		user.setPassword(DigestUtils.md5Hex(pwd));
		user.setPhone(phone);
		user.setBalance(new BigDecimal(0));
		user.setWeight(new BigDecimal(10));
		user.setMobileid(mobileId);
		user.setStatus("1");
		return userMapper.insert(user);
	}


	@Override
	/**
	 * 登录验证
	 */
	public LzUserinfo loginAuth(String phone, String pwd) {
		pwd = DigestUtils.md5Hex(pwd);
		return userMapper.findUserbyPhoneAndPwd(phone, pwd);
	}

	/**
	 * 修改密码
	 */
	public int modifierPassword(String phone,String pwd){
		LzUserinfo user = findUserByPhone(phone);
		if(user==null){
			return 0;
		}
		pwd = DigestUtils.md5Hex(pwd);
		user.setPassword(pwd);
		return userMapper.updateByPrimaryKeySelective(user);
	}
	
	@Override
	/**
	 * 修改用户
	 */
	public int modifierUser(LzUserinfo user) {
		return userMapper.updateByPrimaryKeySelective(user);
	}

/**
 * 邀请注册
 */
	@Override
	@Transactional
	public int inviteRegister(String phone, String pwd,String inviteUserCode) {
		LzUserinfo user = new LzUserinfo();
		Random r = new Random();
		int num = r.nextInt(2146483647)+1000000;
		List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where usercode='"+num+"'");
		if(list!=null&&list.size()>0){//用户编号重复
			num = r.nextInt(2146483647)+1000000;
			list = userMapper.selectBySql("select * from lz_userinfo where usercode='"+num+"'");
		}
		//邀请用户奖励
		List<LzUserinfo> inviteList = userMapper.selectBySql("select * from lz_userinfo where usercode='"+inviteUserCode+"'");
		String invitePhone = "";
		String isaward="0";
		if(inviteList!=null&&inviteList.size()>0){
			LzUserinfo inviteUser = inviteList.get(0);
			invitePhone = inviteUser.getPhone();
			if(inviteUser.getInvitenum()!=null&&inviteUser.getInvitenum()>=5){//邀请人数超过5个，奖励抽奖码一次
				//换触发器解决
//				LzActivecode la = new LzActivecode();
//				la.setActivecode(UUID.randomUUID().toString().replace("-", "").substring(0, 20));
//				la.setBindphone(inviteUser.getPhone());
//				la.setStatus("1");
//				la.setCreatetype("1");
//				la.setCreatetime(new Date());
//				activeMapper.insert(la);
			}else{//否则权重加2
				isaward="1";
				inviteUser.setWeight(inviteUser.getWeight().add(new BigDecimal(2)));
			}
			if(inviteUser.getInvitenum()==null){
				inviteUser.setInvitenum(1);
			}else{
				inviteUser.setInvitenum(inviteUser.getInvitenum()+1);
			}
			userMapper.updateByPrimaryKeySelective(inviteUser);
		}
		
		user.setUsercode(num+"");
		user.setCreatetime(new Date());
		user.setPassword(DigestUtils.md5Hex(pwd));
		user.setPhone(phone);
		user.setBalance(new BigDecimal(0));
		user.setWeight(new BigDecimal(10));
		user.setStatus("1");
		user.setInvitephone(invitePhone);
		user.setIsaward(isaward);
		return userMapper.insert(user);
	}
	
}
