/**
 * 发卡机构相关
 */
package com.jpay.system.service;

import com.jpay.common.exception.JpayBootException;
import com.jpay.system.mapper.SysInstMapper;
import com.jpay.system.pojo.po.SysInstPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IssuerService {
    @Autowired
    private SysSequenceService sysSequenceService;

    @Resource
    private SysInstMapper uInstMapper;

    public String getNextIssu(){
        Long nextVal = sysSequenceService.nextVal("issu");
        if (nextVal>999){
            throw new JpayBootException("序列超限");
        }
        System.out.println("SysSequenceService.getNextIssu:"+nextVal);//debug
        return String.format("%03d", nextVal);
    }

    public String getNextCardTypeOrder(String issuCode){
        Long nextVal=sysSequenceService.nextVal("8"+issuCode);
        if(nextVal>99){
            throw new JpayBootException("序列超限");
        }
        return String.format("%02d", nextVal);
    }

    /**
     * 获取发卡机构
     * @param instId
     * @return
     */
    public SysInstPo getIssuUinst(String instId){
        SysInstPo uInstPo = uInstMapper.selectById(instId);
        if (uInstPo==null){
            return null;
        }
        if (uInstPo.getIssuTag()==1){
            return uInstPo;
        }
        //父机构是顶级管理机构，不用再查了
        if(uInstPo.getParentInstId().equals("0")){
            return null;
        }else {
            //接着查父机构信息
            return getIssuUinst(uInstPo.getParentInstId());
        }
    }
}
