package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class MemberSaveBo {
    private String issuId;
    private String acqId;
    private String name;
    private String phone;
    private String mch;
    private String mchName;
    private String createTellerId;
}
