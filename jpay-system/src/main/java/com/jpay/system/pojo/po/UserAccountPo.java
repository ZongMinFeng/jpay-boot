package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_account")
public class UserAccountPo {
    @TableId(type = IdType.INPUT)
    private String account;
    private String issuId;
    private String memId;
    private String accType;
    private Double currAmt;
    private Double lastAmt;
    private Double faceAmt;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
