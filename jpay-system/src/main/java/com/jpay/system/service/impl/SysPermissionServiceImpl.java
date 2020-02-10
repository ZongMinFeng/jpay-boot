package com.jpay.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpay.common.exception.JpayBootException;
import com.jpay.system.mapper.SysPermissionMapper;
import com.jpay.system.model.TreeModel;
import com.jpay.system.pojo.po.SysPermission;
import com.jpay.system.service.ISysPermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements ISysPermissionService {

	@Resource
	private SysPermissionMapper sysPermissionMapper;


	@Override
	public List<TreeModel> queryListByParentId(String parentId) {
		return null;
	}

	@Override
	public void deletePermission(String id) throws JpayBootException {

	}

	@Override
	public void deletePermissionLogical(String id) throws JpayBootException {

	}

	@Override
	public void addPermission(SysPermission sysPermission) throws JpayBootException {

	}

	@Override
	public void editPermission(SysPermission sysPermission) throws JpayBootException {

	}

	@Override
	public List<SysPermission> queryByUser(String username) {
		return this.sysPermissionMapper.queryByUser(username);
	}

	@Override
	public void deletePermRuleByPermId(String id) {

	}

	@Override
	public List<String> queryPermissionUrlWithStar() {
		return null;
	}
}
