package com.jpay.system.pojo.vo;

import com.jpay.system.pojo.po.SysInstPo;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class InstTreeVo {
    private String instId;
    private String instName;
    private String parentInstId;
    private Integer mngTag;
    private Integer issuTag;
    private Integer acqTag;
    private Date createDate;
    private String createTellerId;
    private Date modiDate;
    private String modiTellerId;

    private List<InstTreeVo> children;

    public InstTreeVo(){

    }

    public InstTreeVo(SysInstPo uInstPo) {
        this.instId= uInstPo.getInstId();
        this.instName= uInstPo.getInstName();
        this.parentInstId= uInstPo.getParentInstId();
        this.mngTag= uInstPo.getMngTag();
        this.issuTag= uInstPo.getIssuTag();
        this.acqTag= uInstPo.getAcqTag();
        this.createDate= uInstPo.getCreateDate();
        this.createTellerId= uInstPo.getCreateTellerId();
        this.modiDate= uInstPo.getModiDate();
        this.modiTellerId= uInstPo.getModiTellerId();
        this.children=new ArrayList<InstTreeVo>();
    }
}
