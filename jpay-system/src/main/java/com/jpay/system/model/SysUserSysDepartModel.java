package com.jpay.system.model;

import com.jpay.system.pojo.po.SysDepart;
import com.jpay.system.pojo.po.SysUser;
import lombok.Data;

/**
 * 包含 SysUser 和 SysDepart 的 Model
 *
 * @author sunjianlei
 */
@Data
public class SysUserSysDepartModel {

    private SysUser sysUser;
    private SysDepart sysDepart;

}
