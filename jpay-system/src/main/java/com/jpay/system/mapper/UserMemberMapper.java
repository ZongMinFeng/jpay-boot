package com.jpay.system.mapper;

import com.jpay.system.pojo.po.UserMemberPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserMemberMapper extends Mapper<UserMemberPo> {
    @Select("SELECT count(*) FROM user_member WHERE issu_id=#{issuId}")
    Integer queryTotalNum(@Param("issuId") String issuId);

    @Select("DELETE FROM user_member WHERE user_id=#{userId} AND issu_id=#{issuId}")
    void deleteByMemIdAndIssuId(@Param("userId") String userId, @Param("issuId") String issuId);

    @Select("SELECT mem_id as memId, user_id as userId, issu_id as issuId, primary_pan as primaryPan, primary_rebate_pan as " +
            "primaryRebatePan FROM user_member WHERE user_id=#{userId} AND issu_id=#{issuId}")
    UserMemberPo queryByMemIdANDIssuId(@Param("userId") String userId, @Param("issuId") String issuId);
}
