package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.AcqMchInfoPo;
import lombok.Data;

import java.util.List;

@Data
public class MchQueryByConVo {
    private Integer allCount;
    private List<AcqMchInfoPo> rows;
}
