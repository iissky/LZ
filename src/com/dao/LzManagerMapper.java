package com.dao;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.pojo.LzManager;

public interface LzManagerMapper {
	@Select("select * from lz_manager where managername=#{username} and managerpwd=#{pwd}")
	public LzManager selectManagerBy(@Param("username") String  username,@Param("pwd") String pwd);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_manager
     *
     * @mbg.generated Tue Jun 25 20:11:01 CST 2019
     */
    int deleteByPrimaryKey(Integer managerid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_manager
     *
     * @mbg.generated Tue Jun 25 20:11:01 CST 2019
     */
    int insert(LzManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_manager
     *
     * @mbg.generated Tue Jun 25 20:11:01 CST 2019
     */
    int insertSelective(LzManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_manager
     *
     * @mbg.generated Tue Jun 25 20:11:01 CST 2019
     */
    LzManager selectByPrimaryKey(Integer managerid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_manager
     *
     * @mbg.generated Tue Jun 25 20:11:01 CST 2019
     */
    int updateByPrimaryKeySelective(LzManager record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_manager
     *
     * @mbg.generated Tue Jun 25 20:11:01 CST 2019
     */
    int updateByPrimaryKey(LzManager record);
}