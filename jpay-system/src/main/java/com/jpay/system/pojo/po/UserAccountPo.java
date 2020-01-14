package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "user_account")
public class UserAccountPo {
    @Id
    private String account;
    private String issuId;
    private String memId;
    private String accType;
    private Double currAmt;
    private Double lastAmt;
    private Double faceAmt;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
