package com.dao;

import com.pojo.LzRedpackage;

public interface LzRedpackageMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_redpackage
     *
     * @mbg.generated Mon Sep 02 19:18:29 CST 2019
     */
    int deleteByPrimaryKey(Integer prid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_redpackage
     *
     * @mbg.generated Mon Sep 02 19:18:29 CST 2019
     */
    int insert(LzRedpackage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_redpackage
     *
     * @mbg.generated Mon Sep 02 19:18:29 CST 2019
     */
    int insertSelective(LzRedpackage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_redpackage
     *
     * @mbg.generated Mon Sep 02 19:18:29 CST 2019
     */
    LzRedpackage selectByPrimaryKey(Integer prid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_redpackage
     *
     * @mbg.generated Mon Sep 02 19:18:29 CST 2019
     */
    int updateByPrimaryKeySelective(LzRedpackage record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_redpackage
     *
     * @mbg.generated Mon Sep 02 19:18:29 CST 2019
     */
    int updateByPrimaryKey(LzRedpackage record);
}