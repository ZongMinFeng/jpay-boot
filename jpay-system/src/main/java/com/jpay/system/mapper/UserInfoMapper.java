package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.UserInfoPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserInfoMapper extends BaseMapper<UserInfoPo> {
    @Select("SELECT user_id as userId, user_name as userName, phone, create_date as createDate, create_teller_id as createTellerId, modi_date as " +
            "modiDate, modi_teller_id as modiTellerId FROM user_info WHERE phone=#{phone}")
    UserInfoPo queryByPhone(@Param("phone") String phone);
}
