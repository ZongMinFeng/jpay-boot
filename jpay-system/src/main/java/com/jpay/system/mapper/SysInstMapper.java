package com.jpay.system.mapper;

import com.jpay.system.pojo.po.SysInstPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysInstMapper extends Mapper<SysInstPo> {

    @Select("SELECT inst_id as instId, parent_inst_id as parentInstId FROM sys_inst WHERE parent_inst_id=#{parentInstId}")
    List<SysInstPo> queryByParentInstId(@Param("parentInstId") String parentInstId);
}
