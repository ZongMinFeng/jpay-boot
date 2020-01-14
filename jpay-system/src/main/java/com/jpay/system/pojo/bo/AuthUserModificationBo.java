package com.jpay.system.pojo.bo;

import lombok.Data;

@Data
public class AuthUserModificationBo {
    private String id;
    private String name;
    private String description;
    private Integer status;
    private String modiTellerId;
}
