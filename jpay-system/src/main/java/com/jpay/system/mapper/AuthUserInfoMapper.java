package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.AuthUserInfoPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AuthUserInfoMapper extends BaseMapper<AuthUserInfoPo> {
    @Select("SELECT id, code, name, pwd, inst_id as instId, exp_date as expDate, description, status, create_date as createDate, create_teller_id as createTellerId, modi_date as modiDate, modi_teller_id as modiTellerId FROM auth_user_info WHERE code=#{code}")
    AuthUserInfoPo queryByCode(@Param("code") String code);

    @Select("SELECT count(*) FROM auth_user_info WHERE inst_id=#{instId}")
    Integer queryTotalNum(@Param("instId") String instId);
}
