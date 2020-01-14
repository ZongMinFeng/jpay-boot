package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class MemberRefundBo {
    private String issuId;
    private String acqId;
    private String voucher;
    private String orgVoucher;
    private String memId;
    private Double refoundAmt;
    private String mid;
    private String mchName;
    private String pid;
    private String posName;
    private String createTellerId;
}
