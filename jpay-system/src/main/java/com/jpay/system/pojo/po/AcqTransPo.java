package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "acq_trans")
public class AcqTransPo {
    @Id
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
