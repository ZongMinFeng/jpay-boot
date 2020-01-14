package com.jpay.system.pojo.po;

import lombok.Data;

import javax.persistence.Id;

@Data
public class SysSequence {
    @Id
    private String sequenceName;
    private Long value;
    private String memo;
}
