package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("acq_voucher")
public class AcqVoucherPo {
    @TableId(type = IdType.INPUT)
    private String voucher;
    private Integer txnId;
    private String txnName;
    private String mid;
    private String mchName;
    private String pid;
    private String posName;
    private String memId;
    private String account;
    private String issuId;
    private String acqId;
    private Double amt;
    private Double bal;
    private Double refundAmt;
    private Integer refundStatus;
    private Date createDate;
    private Date modiDate;
}
