package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("acq_trans")
public class AcqTransPo {
    @TableId(type = IdType.INPUT)
    private String transId;
    private String issuId;
    private String acqId;
    private String account;
    private Integer txnId;
    private String txnIdName;
    private Double amt;
    private Double refundAmt;
    private Double bal;
    private String mid;
    private String mchName;
    private String pid;
    private String posName;
    private Integer settleStatus;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
