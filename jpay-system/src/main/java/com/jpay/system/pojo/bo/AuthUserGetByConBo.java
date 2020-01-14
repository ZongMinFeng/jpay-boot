package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class AuthUserGetByConBo {
    private Integer page;
    private Integer pageSize;
    private String instId;
}
