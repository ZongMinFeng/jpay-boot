package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.AcqVoucherPo;
import lombok.Data;

import java.util.List;

@Data
public class VoucherQueryVo {
    private Long allCount;
    private List<AcqVoucherPo> rows;
}
