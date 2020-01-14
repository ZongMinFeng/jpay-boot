package com.jpay.system.service;

import com.jpay.common.exception.JpayBootException;
import com.jpay.system.mapper.SysSequenceMapper;
import com.jpay.system.pojo.po.SysSequence;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SysSequenceService {
    @Resource
    private SysSequenceMapper sysSequenceMapper;

    @Transactional
    public Long nextVal(String sequenceName){
        SysSequence sysSequence=sysSequenceMapper.selectValueForUpdate(sequenceName);
        if (sysSequence==null){
            throw new JpayBootException("序列名称错");
        }
        Long curr_value=sysSequence.getValue();
        sysSequence.setValue(sysSequence.getValue()+1);
        int i = sysSequenceMapper.updateByPrimaryKey(sysSequence);
        if (i!=1){
            throw new JpayBootException("更新序列失败");
        }
        return curr_value;
    }
}
