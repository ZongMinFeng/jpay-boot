package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_acc_type")
public class SysAccTypePo {
    @TableId(type = IdType.INPUT)
    private String accType;
    private String accName;
    private String issuId;
    private String accOrder;
    private Double faceAmt;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
