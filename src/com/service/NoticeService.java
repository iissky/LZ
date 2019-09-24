package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LzNoticeMapper;
import com.pojo.LzNotice;
import com.pojo.LzUserinfo;
import com.pojo.PageBean;

@Service
public class NoticeService implements INoticeService {
	@Autowired
	LzNoticeMapper noticeMapper;

	/* (non-Javadoc)
	 * @see com.service.INoticeService#findAllUserPage(int, int, java.lang.String)
	 */
	@Override
	public PageBean<LzNotice> findAllNoticePage(int pageIndex, int pageCount,String sql) {
		int totalCount = noticeMapper.selectCountBySql("select count(*) from ("+sql+") a");
		List<LzNotice> list = noticeMapper.selectBySql("select * from ("+sql+") a limit "+((pageIndex-1)*pageCount)+","+pageCount);
		PageBean<LzNotice> pb = new PageBean<LzNotice>();
		pb.setPageIndex(pageIndex);
		pb.setTotalCount(totalCount);
		pb.setPageCount(pageCount);
		pb.setList(list);
		pb.setTotalPage(totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount)+1);
		return pb;
	}

	@Override
	public int addNotice(String title, String content, String status) {
		LzNotice n = new LzNotice();
		n.setTitle(title);
		n.setContent(content);
		n.setCreatetime(new Date());
		n.setStatus(status);
		return noticeMapper.insert(n);
	}

	@Override
	public LzNotice findNoticeById(String noticeid) {
		return noticeMapper.selectByPrimaryKey(Integer.valueOf(noticeid));
	}
	
	@Override
	public int updateNotice(LzNotice n) {
		return noticeMapper.updateByPrimaryKeySelective(n);
	}
}
