package com.api.json;

import java.util.List;

import com.pojo.FriendBean;
import com.pojo.LzFriendapply;

public class FriendApplyJson extends BaseJson{
	List<FriendBean> applyList;

	public List<FriendBean> getApplyList() {
		return applyList;
	}

	public void setApplyList(List<FriendBean> applyList) {
		this.applyList = applyList;
	}
	
}
