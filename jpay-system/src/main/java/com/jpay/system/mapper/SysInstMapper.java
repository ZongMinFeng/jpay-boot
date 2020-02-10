package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.SysInstPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface SysInstMapper extends BaseMapper<SysInstPo> {

    @Select("SELECT inst_id as instId, parent_inst_id as parentInstId FROM sys_inst WHERE parent_inst_id=#{parentInstId}")
    List<SysInstPo> queryByParentInstId(@Param("parentInstId") String parentInstId);
}
