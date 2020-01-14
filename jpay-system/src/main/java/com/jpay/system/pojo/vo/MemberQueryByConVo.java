package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.UserInfoPo;
import lombok.Data;

import java.util.List;

@Data
public class MemberQueryByConVo {
    private Integer allCount;
    private List<UserInfoPo> rows;
}
