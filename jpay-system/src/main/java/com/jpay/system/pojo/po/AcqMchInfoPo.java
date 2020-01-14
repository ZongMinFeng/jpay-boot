package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "acq_mch_info")
public class AcqMchInfoPo {
    @Id
    private String mid;
    private String acqId;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
