package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_inst")
public class SysInstPo {
    @TableId(type = IdType.INPUT)
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
