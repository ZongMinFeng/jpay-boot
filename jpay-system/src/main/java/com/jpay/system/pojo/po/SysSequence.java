package com.jpay.system.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class SysSequence {
    @TableId(type = IdType.INPUT)
    private String sequenceName;
    private Long value;
    private String memo;
}
