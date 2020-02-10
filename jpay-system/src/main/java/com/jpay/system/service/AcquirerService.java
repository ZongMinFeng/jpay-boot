package com.jpay.system.service;

import com.jpay.common.exception.JpayBootException;
import com.jpay.system.mapper.SysInstMapper;
import com.jpay.system.pojo.po.SysInstPo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AcquirerService {
    @Resource
    private SysSequenceService sysSequenceService;

    @Resource
    private SysInstMapper uInstMapper;

    public String getNextAcq(){
        Long nextVal = sysSequenceService.nextVal("acq");
        if (nextVal>9999){
            throw new JpayBootException("序列超限");
        }
        return String.format("%04d", nextVal);
    }

    /**
     * 获取受理机构
     * @param instId
     * @return
     */
    public SysInstPo getAcqUinst(String instId){
        SysInstPo uInstPo = uInstMapper.selectById(instId);
        if (uInstPo==null){
            return null;
        }
        if (uInstPo.getAcqTag()==1){
            return uInstPo;
        }
        //父机构是顶级管理机构，不用再查了
        if(uInstPo.getParentInstId().equals("0")){
            return null;
        }else {
            //接着查父机构信息
            return getAcqUinst(uInstPo.getParentInstId());
        }
    }
}
