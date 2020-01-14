package com.jpay.system.pojo.panto;

import lombok.Data;

import java.util.Date;

@Data
public class RefundTo {
    private String issuId;
    private String acqId;
    private String account;
    private Double refundAmt;
    private String transId;
    private String orgTransId;
    private String mid;
    private String mchName;
    private String pid;
    private String posName;
    private Date createDate;
    private String createTellerId;

    private Double bal;
}
