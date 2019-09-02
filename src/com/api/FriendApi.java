package com.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.json.BaseJson;
import com.api.json.FriendApplyJson;
import com.api.json.FriendJson;
import com.dao.LzFriendapplyMapper;
import com.dao.LzFriendlistMapper;
import com.dao.LzUserinfoMapper;
import com.pojo.FriendBean;
import com.pojo.LzFriendapply;
import com.pojo.LzFriendlist;
import com.pojo.LzUserinfo;

/**
 * 好友相关API
 * @author gy
 *
 */
@Controller
@RequestMapping("/friend")
public class FriendApi {
	@Autowired
	LzFriendlistMapper friendListMapper;
	@Autowired
	LzFriendapplyMapper friendapplyMapper;
	@Autowired
	LzUserinfoMapper userMapper;
	/**
	 * 好友列表
	 * @param userPhone
	 * @return
	 */
	@RequestMapping("/friendlist")
	public @ResponseBody FriendJson friendList(String userPhone){
		try{
			FriendJson fj = new FriendJson();
			//查询好友列表
			List<Map<String, Object>> list = friendListMapper.findMapBysql("select b.Phone,b.username,b.picpath,a.friendusercode from lz_friendlist a,lz_userinfo b where a.friendphone=b.Phone and a.userphone='"+userPhone+"'");
			//查询好友申请数量
			List<Map<String, Object>> applylist = friendapplyMapper.findMapBysql("select count(faid) count from lz_friendapply where userphone='"+userPhone+"' and status='0'");
			fj.setFriendApplyCount((long)applylist.get(0).get("count"));
			fj.setResultCode("1001");
			fj.setResultMess("成功");
			List<FriendBean> flist = new ArrayList<FriendBean>();
			for (Map<String, Object> map : list) {
				FriendBean fb = new FriendBean();
				fb.setFriendPhone((String)map.get("Phone"));
				fb.setNickName((String)map.get("username"));
				fb.setPicpath((String)map.get("picpath"));
				fb.setUsercode((String)map.get("friendusercode"));
				flist.add(fb);
			}
			fj.setFriendlist(flist);
			return fj;
		}catch (Exception e) {
			e.printStackTrace();
			FriendJson fj = new FriendJson();
			fj.setResultCode("4001");
			fj.setResultMess("失败");
			return fj;
		}
	}
	/**
	 * 搜索好友信息
	 * @param usercode
	 * @return
	 */
	@RequestMapping("/searchFriend")
	public @ResponseBody Map searchFriend(String userPhone,String usercode){
		try{
			Map map = new HashMap();
			List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where usercode='"+usercode+"'");
			if(list!=null&&list.size()>0){
				LzUserinfo user = list.get(0);
				//判断该用户是不是已经是好友
				List<LzFriendlist> fl = friendListMapper.findBySql("select * from lz_friendlist where userphone='"+userPhone+"' and friendphone='"+user.getPhone()+"'");
				if(fl!=null&&fl.size()>0){
					map.put("isfriend", "yes");
				}else{
					map.put("isfriend", "no");
				}
				map.put("resultCode", "1001");
				map.put("resultMess", "成功");
				map.put("userphone", user.getPhone());
				map.put("nickName", user.getUsername());
				map.put("usercode", user.getUsercode());
				map.put("picpath", user.getPicpath());
			}
			return map;
		}catch (Exception e) {
			e.printStackTrace();
			Map map = new HashMap();
			map.put("resultCode", "1001");
			map.put("resultMess", "成功");
			return map;
		}
		
	}
	
	/**
	 * 发起好友申请
	 * @param userPhone 发起号
	 * @param friendUserCode 接收方用户编码
	 * @return
	 */
	@RequestMapping("/friendApply")
	public @ResponseBody BaseJson friendApply(String userPhone,String friendUserCode){
		BaseJson bj = new BaseJson();
		LzFriendapply lfa = new LzFriendapply();
		lfa.setUserphone(userPhone);
		List<LzUserinfo> userlist = userMapper.selectBySql("select * from lz_userinfo where phone='"+userPhone+"'");
		if(userlist!=null&&userlist.size()>0){
			lfa.setUsercode(userlist.get(0).getUsercode());
		}
		
		List<LzUserinfo> friendlist = userMapper.selectBySql("select * from lz_userinfo where usercode='"+friendUserCode+"'");
		if(friendlist!=null&&friendlist.size()>0){
			lfa.setFriendphone(friendlist.get(0).getPhone());
			lfa.setFriendusercode(friendUserCode);
		}
		lfa.setApplytime(new Date());
		lfa.setStatus("0");
		friendapplyMapper.insert(lfa);
		bj.setResultCode("1001");
		bj.setResultMess("成功");
		return bj;
	}
	
	/**
	 * 获取好友申请列表
	 * @param userPhone
	 * @return
	 */
	@RequestMapping("/friendApplyList")
	public @ResponseBody FriendApplyJson friendApplyList(String userPhone){
		try{
			FriendApplyJson faj = new FriendApplyJson();
			List<Map<String, Object>> list = friendapplyMapper.findMapBysql("select b.Phone,b.username,b.picpath,b.usercode,a.faid from lz_friendapply a,lz_userinfo b where a.friendphone='"+userPhone+"' and a.userphone=b.Phone");
			List<FriendBean> fbList = new ArrayList<>();
			for (Map<String, Object> map : list) {
				FriendBean fb = new FriendBean();
				fb.setFaid(map.get("faid")+"");
				fb.setFriendPhone((String)map.get("Phone"));
				fb.setNickName((String)map.get("username"));
				fb.setPicpath((String)map.get("picpath"));
				fb.setUsercode((String)map.get("usercode"));
				fbList.add(fb);
				faj.setApplyList(fbList);
			}
			faj.setResultCode("1001");
			faj.setResultMess("成功");
			return faj;
		}catch (Exception e) {
			FriendApplyJson faj = new FriendApplyJson();
			e.printStackTrace();
			faj.setResultCode("4001");
			faj.setResultMess("失败");
			return faj;
		}
	}
	/**
	 * 处理好友请求
	 * @param faid 请求编号
	 * @param status 状态：1.同意  2.拒绝
	 * @return
	 */
	@RequestMapping("/friendApplyDeal")
	public @ResponseBody BaseJson friendApplyDeal(String faid,String status){
		BaseJson bj = new BaseJson();
		try{
			if("1".equals(status)){//同意
				LzFriendapply apply = friendapplyMapper.selectByPrimaryKey(Integer.valueOf(faid));
				//单向添加好友关系
				LzFriendlist friendList = new LzFriendlist();
				friendList.setUserphone(apply.getUserphone());
				friendList.setFriendphone(apply.getFriendphone());
				friendList.setFriendusercode(apply.getFriendusercode());
				friendListMapper.insert(friendList);
				
				//反向添加好友关系
				LzFriendlist f = new LzFriendlist();
				f.setUserphone(apply.getFriendphone());
				f.setFriendphone(apply.getUserphone());
				f.setFriendusercode(apply.getUsercode());
				friendListMapper.insert(f);
				
			}else if("2".equals(status)){//拒绝
				//暂时不做处理
			}
			//将申请状态修改
			friendapplyMapper.updateStatus(faid, status);
			bj.setResultCode("1001");
			bj.setResultMess("成功");
			return bj;
		}catch (Exception e) {
			e.printStackTrace();
			bj.setResultCode("4001");
			bj.setResultMess("失败");
			return bj;
		}
	}
}














