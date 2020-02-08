package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.SysUserDepart;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

public interface SysUserDepartMapper extends BaseMapper<SysUserDepart>{
	
	List<SysUserDepart> getUserDepartByUid(@Param("userId") String userId);
}
