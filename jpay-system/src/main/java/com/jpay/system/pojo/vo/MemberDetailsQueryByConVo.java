package com.jpay.system.pojo.vo;

import lombok.Data;

@Data
public class MemberDetailsQueryByConVo {
    private String memId;
    private String userId;
    private String issuId;
    private String name;
    private String phone;
    //余额
    private Double bal;
}
