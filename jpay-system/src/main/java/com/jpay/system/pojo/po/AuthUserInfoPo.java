package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "auth_user_info")
public class AuthUserInfoPo {
    @Id
    private String id;
    private String code;
    private String name;
    private String pwd;
    private String instId;
    private Date expDate;
    private String description;
    private Integer status;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
