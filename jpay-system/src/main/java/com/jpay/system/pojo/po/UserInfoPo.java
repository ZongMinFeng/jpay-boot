package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "user_info")
public class UserInfoPo {
    @Id
    private String userId;
    private String userName;
    private String phone;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
