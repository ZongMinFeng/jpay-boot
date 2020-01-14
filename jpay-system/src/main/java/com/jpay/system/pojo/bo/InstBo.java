package com.jpay.system.pojo.bo;

import lombok.Data;

import java.util.Date;

@Data
public class InstBo {
    private String instId;
    private String instName;
    private String parentInstId;
    private Integer mngTag;
    private Integer issuTag;
    private Integer acqTag;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
