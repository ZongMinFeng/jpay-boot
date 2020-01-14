package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class MemberDetailsQueryByConBo {
    private String issuId;
    private String userId;
    private String phone;
    private Integer flag;
}
