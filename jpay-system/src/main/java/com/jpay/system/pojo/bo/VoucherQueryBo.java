package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class VoucherQueryBo {
    private Integer page;
    private Integer pageSize;
    private String issuId;
    private String acqId;
    private String memId;
    private String account;
    private Integer flag;
    private String mch;
    private String mchName;
    private String pos;
    private String posName;
    private String createTellerId;
}
