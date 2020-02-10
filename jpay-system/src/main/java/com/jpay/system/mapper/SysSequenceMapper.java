package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.SysSequence;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SysSequenceMapper extends BaseMapper<SysSequence> {
    //悲观锁
    @Select("select sequence_name as sequenceName, value, memo FROM sys_sequence WHERE sequence_name=#{sequenceName} FOR UPDATE")
    SysSequence selectValueForUpdate(@Param("sequenceName") String sequenceName);
}
