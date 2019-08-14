package com.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pojo.LzAuthcode;

public interface LzAuthcodeMapper {
	@Select("select * from lz_authcode where phone=#{phone} and Authcode=#{code} and Createtime>ADDDATE(NOW(),INTERVAL -3 minute)")
	LzAuthcode findAuthcodeTothree(@Param("phone") String phone,@Param("code") String code);
    int deleteByPrimaryKey(Integer codeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_authcode
     *
     * @mbg.generated Wed Jul 03 11:02:58 CST 2019
     */
    int insert(LzAuthcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_authcode
     *
     * @mbg.generated Wed Jul 03 11:02:58 CST 2019
     */
    int insertSelective(LzAuthcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_authcode
     *
     * @mbg.generated Wed Jul 03 11:02:58 CST 2019
     */
    LzAuthcode selectByPrimaryKey(Integer codeid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_authcode
     *
     * @mbg.generated Wed Jul 03 11:02:58 CST 2019
     */
    int updateByPrimaryKeySelective(LzAuthcode record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_authcode
     *
     * @mbg.generated Wed Jul 03 11:02:58 CST 2019
     */
    int updateByPrimaryKey(LzAuthcode record);
}