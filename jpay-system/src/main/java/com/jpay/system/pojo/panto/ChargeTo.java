package com.jpay.system.pojo.panto;

import lombok.Data;

import java.util.Date;

@Data
public class ChargeTo {
    private String issuId;
    private String acqId;
    private String pan;
    private Double amt;
    private String transId;
    private String mch;
    private String mchName;
    private String pos;
    private String posName;
    private Date createDate;
    private String createTellerId;
}
