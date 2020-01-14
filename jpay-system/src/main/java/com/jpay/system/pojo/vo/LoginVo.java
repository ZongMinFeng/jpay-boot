package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.SysInstPo;
import lombok.Data;

@Data
public class LoginVo {
    private String loginId;
    //当前机构信息
    private SysInstPo instInfo;
    //发卡机构信息
    private SysInstPo issuInstInfo;
    //收单机构信息
    private SysInstPo acqInstInfo;
    //transId
    private String transId;
}
