package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("auth_login_info")
public class AuthLoginInfo {
    @TableId(type = IdType.INPUT)
    private String id;
    private String userId;
    private String randKey;
    private Integer status;
    private Date createDate;
    private Date modiDate;
}
