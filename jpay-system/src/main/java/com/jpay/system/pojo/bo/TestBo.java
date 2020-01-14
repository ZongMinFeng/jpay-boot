package com.jpay.system.pojo.bo;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class TestBo {
    private Integer code;
    private JSONObject data;

    public TestBo(){
        this.data=new JSONObject();
    }
}
