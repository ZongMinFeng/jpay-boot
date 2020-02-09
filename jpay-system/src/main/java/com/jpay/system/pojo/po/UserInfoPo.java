package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_info")
public class UserInfoPo {
    @TableId(type = IdType.INPUT)
    private String userId;
    private String userName;
    private String phone;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
