package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("user_member")
public class UserMemberPo {
    @TableId(type = IdType.INPUT)
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
