package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "auth_login_info")
public class AuthLoginInfo {
    @Id
    private String id;
    private String userId;
    private String randKey;
    private Integer status;
    private Date createDate;
    private Date modiDate;
}
