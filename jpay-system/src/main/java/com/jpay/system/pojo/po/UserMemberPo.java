package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "user_member")
public class UserMemberPo {
    @Id
    private String memId;
    private String userId;
    private String issuId;
    private String primaryPan;
    private String primaryRebatePan;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
