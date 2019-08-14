package com.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LzAccountMapper;
import com.dao.LzActivecodeMapper;
import com.pojo.LzActivecode;
import com.pojo.LzUserinfo;
import com.pojo.PageBean;
@Service
public class ActiveCodeService implements IActiveCodeService{
	@Autowired
	LzActivecodeMapper activeMapper;
	
	
	public void setActiveMapper(LzActivecodeMapper activeMapper) {
		this.activeMapper = activeMapper;
	}


	@Override
	public PageBean<LzActivecode> findActiveCodePage(int pageIndex, int pageCount, String sql) {
		int totalCount = activeMapper.selectCountBySql("select count(*) from ("+sql+") a");
		List<LzActivecode> list = activeMapper.selectBySql("select * from ("+sql+") a limit "+((pageIndex-1)*pageCount)+","+pageCount);
		PageBean<LzActivecode> pb = new PageBean<LzActivecode>();
		pb.setPageIndex(pageIndex);
		pb.setTotalCount(totalCount);
		pb.setPageCount(pageCount);
		pb.setList(list);
		pb.setTotalPage(totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount)+1);
		return pb;
	}


	@Override
	public int createNewActiveCode(String activeCode, String bindPhone) {
		LzActivecode la = new LzActivecode();
		la.setActivecode(activeCode);
		la.setBindphone(bindPhone);
		la.setStatus("1");
		la.setCreatetype("2");
		la.setCreatetime(new Date());
		return activeMapper.insert(la);
	}
	
	@Override
	public List<LzActivecode> findActiveCodeByPhone(String phone) {
		return activeMapper.selectBySql("select * from lz_activecode where bindphone='"+phone+"' and status='1'");
	}


	@Override
	public String activeCodeAuth(String phone, String activeCode) {
		//查询激活码是否可用
		List<LzActivecode> list = activeMapper.selectBySql("select * from lz_activecode where activecode='"+activeCode+"'");
		if(list!=null&&list.size()>0){
			LzActivecode code = list.get(0);
			if("2".equals(code.getStatus())){
				return "3";//已使用
			}
			//将该激活码状态修改
			code.setStatus("2");
			code.setUsephone(phone);
			activeMapper.updateByPrimaryKeySelective(code);
			return "1";
		}else{
			return "2";//没有激活码
		}
	}
}
