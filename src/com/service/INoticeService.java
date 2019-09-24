package com.service;

import com.pojo.LzNotice;
import com.pojo.PageBean;

public interface INoticeService {

	/**
	 * 分页查询公告信息
	 */
	PageBean<LzNotice> findAllNoticePage(int pageIndex, int pageCount, String sql);
	
	/**
	 * 新增公告
	 * @param title
	 * @param content
	 * @param status
	 * @return
	 */
	public int addNotice(String title,String content,String status);
	
	/**
	 * 修改公告
	 * @param noticeid
	 * @param title
	 * @param content
	 * @param status
	 * @return
	 */
	public int updateNotice(LzNotice n);
	
	/**
	 * 根据主键查询公告
	 * @param noticeid
	 * @return
	 */
	public LzNotice findNoticeById(String noticeid);

}