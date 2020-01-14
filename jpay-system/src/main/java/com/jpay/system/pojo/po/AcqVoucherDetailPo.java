package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "acq_voucher_detail")
public class AcqVoucherDetailPo {
    @Id
    private String transId;
    private String voucher;
    private String account;
    private Double amt;
    private Double refundAmt;
    private String orgTransId;
}
