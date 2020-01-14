package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_acc_type")
public class SysAccTypePo {
    @Id
    private String accType;
    private String accName;
    private String issuId;
    private String accOrder;
    private Double faceAmt;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
