package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class PosQueryByConBo {
    private Integer page;
    private Integer pageSize;
    private String acqId;
    private String mid;
}
