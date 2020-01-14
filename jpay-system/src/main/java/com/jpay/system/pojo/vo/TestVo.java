package com.jpay.system.pojo.vo;

import lombok.Data;

@Data
public class TestVo {
    private Integer code;
    private TestDate data;

    public TestVo(){
        this.data = new TestDate();
    }
}
