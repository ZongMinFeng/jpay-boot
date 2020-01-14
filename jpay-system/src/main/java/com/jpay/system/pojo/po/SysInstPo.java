package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "sys_inst")
public class SysInstPo {
    @Id
    private String instId;
    private String instName;
    private String parentInstId;
    private Integer mngTag;
    private Integer issuTag;
    private String issuCode;
    private Integer acqTag;
    private String acqCode;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
