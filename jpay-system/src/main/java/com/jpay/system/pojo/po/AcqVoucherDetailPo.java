package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("acq_voucher_detail")
public class AcqVoucherDetailPo {
    @TableId(type = IdType.INPUT)
    private String transId;
    private String voucher;
    private String account;
    private Double amt;
    private Double refundAmt;
    private String orgTransId;
}
