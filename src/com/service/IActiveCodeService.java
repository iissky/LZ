package com.service;

import java.util.List;

import com.pojo.LzActivecode;
import com.pojo.PageBean;

public interface IActiveCodeService {
	public PageBean<LzActivecode> findActiveCodePage(int pageIndex,int pageCount,String sql);
	
	/**
	 * 后台手动生成激活码
	 * @param activeCode
	 * @param bindPhone
	 * @return
	 */
	public int createNewActiveCode(String activeCode,String bindPhone);
	
	/**
	 * 根据手机查看当前可用的激活码集合
	 * @param phone
	 * @return
	 */
	public List<LzActivecode> findActiveCodeByPhone(String phone);
	
	/**
	 * 手机端发送激活码后台验证
	 * @param phone
	 * @param activeCode
	 * @return 1.成功 2.没有激活码  3.激活码已被使用
	 */
	public String activeCodeAuth(String phone,String activeCode);
}
