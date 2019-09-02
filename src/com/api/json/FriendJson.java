package com.api.json;

import java.util.List;

import com.pojo.FriendBean;

public class FriendJson extends BaseJson{
	long friendApplyCount;//好友申请数量
	List<FriendBean> friendlist;//好友列表

	public List<FriendBean> getFriendlist() {
		return friendlist;
	}

	public void setFriendlist(List<FriendBean> friendlist) {
		this.friendlist = friendlist;
	}

	public long getFriendApplyCount() {
		return friendApplyCount;
	}

	public void setFriendApplyCount(long friendApplyCount) {
		this.friendApplyCount = friendApplyCount;
	}
	
	
}
