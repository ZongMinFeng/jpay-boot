package com.jpay.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpay.common.exception.JpayBootException;
import com.jpay.system.model.TreeModel;
import com.jpay.system.pojo.po.SysPermission;

import java.util.List;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
public interface ISysPermissionService extends IService<SysPermission> {
	
	public List<TreeModel> queryListByParentId(String parentId);
	
	/**真实删除*/
	public void deletePermission(String id) throws JpayBootException;
	/**逻辑删除*/
	public void deletePermissionLogical(String id) throws JpayBootException;
	
	public void addPermission(SysPermission sysPermission) throws JpayBootException;
	
	public void editPermission(SysPermission sysPermission) throws JpayBootException;
	
	public List<SysPermission> queryByUser(String username);
	
	/**
	 * 根据permissionId删除其关联的SysPermissionDataRule表中的数据
	 * 
	 * @param id
	 * @return
	 */
	public void deletePermRuleByPermId(String id);
	
	/**
	  * 查询出带有特殊符号的菜单地址的集合
	 * @return
	 */
	public List<String> queryPermissionUrlWithStar();
}
