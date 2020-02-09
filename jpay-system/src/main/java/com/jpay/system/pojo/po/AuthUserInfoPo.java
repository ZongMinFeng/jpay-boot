package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("auth_user_info")
public class AuthUserInfoPo {
    @TableId(type = IdType.INPUT)
    private String id;
    private String code;
    private String name;
    private String pwd;
    private String instId;
    private Date expDate;
    private String description;
    private Integer status;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
