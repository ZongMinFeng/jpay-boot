package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "acq_voucher")
public class AcqVoucherPo {
    @Id
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
