package com.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dao.LzWeightaccountMapper;
@Service
public class WeightAccountService implements IWeightAccountService{
	@Autowired
	LzWeightaccountMapper weighetAccountMapper;
	
	public void setWeighetAccountMapper(LzWeightaccountMapper weighetAccountMapper) {
		this.weighetAccountMapper = weighetAccountMapper;
	}

	@Override
	public BigDecimal getSumByPhoneAndWeightSetId(String phone, int wsid) {
		BigDecimal sum = weighetAccountMapper.getSumByPhoneAndWeightSetId(phone, wsid);
		if(sum==null){
			return new BigDecimal(0);
		}
		return sum;
	}

}
