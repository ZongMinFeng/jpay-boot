package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class MchQueryByConBo {
    private Integer page;
    private Integer pageSize;
    private String acqId;
}
