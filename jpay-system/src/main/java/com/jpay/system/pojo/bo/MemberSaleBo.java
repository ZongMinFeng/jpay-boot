package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class MemberSaleBo {
    private String issuId;
    private String acqId;
    private String transId;
    private String memId;
    private Double amt;
    private String mch;
    private String mchName;
    private String pos;
    private String posName;
    private String createTellerId;
}
