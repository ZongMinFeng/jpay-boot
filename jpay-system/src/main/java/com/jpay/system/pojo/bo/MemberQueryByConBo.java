package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class MemberQueryByConBo {
    private Integer page;
    private Integer pageSize;
    private String issuId;
}
