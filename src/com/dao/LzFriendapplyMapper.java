package com.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.pojo.LzFriendapply;

public interface LzFriendapplyMapper {
	@Update("update lz_friendapply set status=#{status} where faid=#{faid}")
	int updateStatus(@Param("faid") String faid,@Param("status") String status);
	@Select("${sql}")
	List<Map<String, Object>> findMapBysql(@Param("sql") String sql);
	
	@Select("${sql}")
	List<LzFriendapply> findBySql(@Param("sql") String sql);
	
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_friendapply
     *
     * @mbg.generated Thu Aug 29 17:37:09 CST 2019
     */
    int deleteByPrimaryKey(Integer faid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_friendapply
     *
     * @mbg.generated Thu Aug 29 17:37:09 CST 2019
     */
    int insert(LzFriendapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_friendapply
     *
     * @mbg.generated Thu Aug 29 17:37:09 CST 2019
     */
    int insertSelective(LzFriendapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_friendapply
     *
     * @mbg.generated Thu Aug 29 17:37:09 CST 2019
     */
    LzFriendapply selectByPrimaryKey(Integer faid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_friendapply
     *
     * @mbg.generated Thu Aug 29 17:37:09 CST 2019
     */
    int updateByPrimaryKeySelective(LzFriendapply record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lz_friendapply
     *
     * @mbg.generated Thu Aug 29 17:37:09 CST 2019
     */
    int updateByPrimaryKey(LzFriendapply record);
}