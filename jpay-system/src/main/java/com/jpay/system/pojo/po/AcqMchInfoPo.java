package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("acq_mch_info")
public class AcqMchInfoPo {
    @TableId(type = IdType.INPUT)
    private String mid;
    private String acqId;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;
}
