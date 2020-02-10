package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.UserAccountPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface UserAccountMapper extends BaseMapper<UserAccountPo> {
    @Update("UPDATE user_account SET curr_amt=curr_amt+#{amt} WHERE account=#{account}")
    Integer updateAmt(@Param("amt") Double amt, @Param("account") String account);
}
