package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.SysAccTypePo;
import lombok.Data;

import java.util.List;

@Data
public class CardTypeGetByConVo {
    private Integer allCount;
    private List<SysAccTypePo> rows;
}
