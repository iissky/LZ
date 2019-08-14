package com.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.api.json.DriftJson;
import com.api.json.GameRoundJson;
import com.dao.LzAccountMapper;
import com.dao.LzDealorderMapper;
import com.dao.LzGameroundMapper;
import com.dao.LzUsergamerecordMapper;
import com.dao.LzUserinfoMapper;
import com.pojo.LzAccount;
import com.pojo.LzDealorder;
import com.pojo.LzGameround;
import com.pojo.LzUsergamerecord;
import com.pojo.LzUserinfo;
import com.pojo.PageBean;
@Service
public class GameRoundService implements IGameRoundService{
	@Autowired
	LzGameroundMapper gameMapper;
	@Autowired
	LzUserinfoMapper userMapper;
	@Autowired
	LzDealorderMapper orderMapper;
	@Autowired
	LzUsergamerecordMapper gamerecordMapper;
	@Autowired
	LzAccountMapper accountMapper;
	
	public void setAccountMapper(LzAccountMapper accountMapper) {
		this.accountMapper = accountMapper;
	}

	public void setGamerecordMapper(LzUsergamerecordMapper gamerecordMapper) {
		this.gamerecordMapper = gamerecordMapper;
	}

	public void setOrderMapper(LzDealorderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	public void setUserMapper(LzUserinfoMapper userMapper) {
		this.userMapper = userMapper;
	}

	public void setGameMapper(LzGameroundMapper gameMapper) {
		this.gameMapper = gameMapper;
	}

	@Override
	public PageBean<LzGameround> findGameRoundByPage(int pageIndex, int pageCount, String sql) {
		int totalCount = gameMapper.selectCountBySql("select count(*) from ("+sql+") a");
		List<LzGameround> list = gameMapper.selectGameRoundBySql("select * from ("+sql+") a limit "+((pageIndex-1)*pageCount)+","+pageCount);
		PageBean<LzGameround> pb = new PageBean<LzGameround>();
		pb.setPageIndex(pageIndex);
		pb.setTotalCount(totalCount);
		pb.setPageCount(pageCount);
		pb.setList(list);
		pb.setTotalPage(totalCount%pageCount==0?totalCount/pageCount:(totalCount/pageCount)+1);
		return pb;
	}

	@Override
	@Transactional
	public void createGameRound() {
		try {
			String path = this.getClass().getResource("/").getPath();
			InputStream in = new FileInputStream(path+"/gameset.properties");
			Properties p = new Properties();
			p.load(in);
			String gameLimit = p.getProperty("gameLimit");
			in.close();
			BigDecimal limit = null;
			if(gameLimit==null){
				limit = new BigDecimal(0);
			}else{
				limit = new BigDecimal(gameLimit);
			}
			//获取当前有效游戏轮次
			LzGameround validlg = getValidGameRound();
			if(validlg!=null){
				//将没有点击钱币的累计记录到记录表
				gamerecordMapper.addGameRecord(validlg.getGameid(), limit);
				
				//将上轮游戏设置为已结束
				gameMapper.setGameInvalid();
			}
		
			LzGameround lg = new LzGameround();
			lg.setCreatetime(new Date());
			lg.setStatus("1");
			lg.setPtotal(new BigDecimal(gameLimit));
			Map<String, BigDecimal> map = userMapper.getAllBalanceAndWeight();
			if(map==null){
				lg.setBaltotal(new BigDecimal(0));
				lg.setWeitotal(new BigDecimal(0));
			}else{
				lg.setBaltotal(map.get("totalBalance"));
				lg.setWeitotal(map.get("totalWeight"));
			}
			gameMapper.insert(lg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public LzGameround getValidGameRound() {
		List<LzGameround> list = gameMapper.selectGameRoundBySql("select * from lz_gameround where status='1' order by createtime limit 0,1");
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public GameRoundJson getUserGameRoundJson(String phone,LzGameround lg) {
		GameRoundJson grj = new GameRoundJson();
		List<LzUserinfo> list = userMapper.selectBySql("select * from lz_userinfo where phone='"+phone+"' and status='1'");
		if(list!=null&&list.size()>0){
			LzUserinfo user = list.get(0);
			grj.setGameId(lg.getGameid());
			
			Map<String, BigDecimal> map = userMapper.getAllBalanceAndWeight();
			//获取全网当前有效用户总和
			BigDecimal totalBalance = map.get("totalBalance");
			BigDecimal totalWeight = map.get("totalWeight");
			
			grj.setUserMoney(user.getBalance());
			grj.setUserWeight(user.getWeight());
			
			//查询本轮是否已经产生漂流瓶
			List<LzUsergamerecord> gameRecordList = gamerecordMapper.selectBySql("select * from lz_usergamerecord where gameid='"+lg.getGameid()+"' and phone='"+phone+"'");
			if(!(gameRecordList!=null&&gameRecordList.size()>0)){
				//计算当前用户产币金额
				BigDecimal money = user.getWeight().divide(totalWeight,4,BigDecimal.ROUND_HALF_UP).multiply(lg.getPtotal());
				
				//记录下这次产币记录
				LzUsergamerecord lug = new LzUsergamerecord();
				lug.setGameid(lg.getGameid());
				lug.setCreatetime(new Date());
				lug.setPhone(phone);
				lug.setWeight(user.getWeight());
				lug.setReceivable(money);
				lug.setStatus("1");
				gamerecordMapper.insert(lug);
			}
			
			
			//查询48小时内未收的钱币
			List<LzUsergamerecord> recordlist = gamerecordMapper.getUncollectMoney(phone);
			List<DriftJson> moneys = new ArrayList<>();
			if(recordlist!=null&&recordlist.size()>0){
				for (LzUsergamerecord recod : recordlist) {
					DriftJson d = new DriftJson();
					d.setMoneyId(recod.getGamerecordid());
					d.setMoney(recod.getReceivable());
					moneys.add(d);
				}
				grj.setMoneys(moneys);
			}
			grj.setResultCode("1001");
			grj.setResultMess("成功");
			return grj;
		}else{
			grj.setResultCode("4003");
			grj.setResultMess("没有该手机号用户");
			return grj;
		}
	}

	@Override
	@Transactional
	public int rechargeMoney(String userPhone,int moneyid) {
		//修改漂流瓶状态
		LzUsergamerecord record = gamerecordMapper.selectByPrimaryKey(moneyid);
		record.setStatus("2");
		gamerecordMapper.updateByPrimaryKeySelective(record);
		//修改余额
		userMapper.dialMoneyOrder(userPhone,record.getReceivable());
		//记录交易信息
		LzDealorder order = new LzDealorder();
		order.setDealamount(record.getReceivable());
		order.setDealphone(record.getPhone());
		order.setDealtime(new Date());
		order.setDealtype("2");
		order.setGameid(record.getGameid());
		int orderid = orderMapper.insert(order);
		//记账
		LzAccount account = new LzAccount();
		account.setAccounttime(new Date());
		account.setDealid(orderid);
		account.setAccountphone(record.getPhone());
		account.setAccounttype("1");
		account.setAmount(record.getReceivable());
		return accountMapper.insert(account);
	}

}
