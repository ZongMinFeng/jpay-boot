package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.AuthUserInfoPo;
import lombok.Data;

import java.util.List;

@Data
public class AuthUserGetByConVo {
    private Integer allCount;
    private List<AuthUserInfoPo> rows;
}
