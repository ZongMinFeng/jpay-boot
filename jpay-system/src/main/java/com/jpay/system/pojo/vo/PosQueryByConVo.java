package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.AcqPosInfoPo;
import lombok.Data;

import java.util.List;

@Data
public class PosQueryByConVo {
    private Integer allCount;
    private List<AcqPosInfoPo> rows;
}
