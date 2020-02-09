package com.jpay.test;

import com.jpay.system.mapper.AcqMchInfoMapper;
import com.jpay.system.pojo.po.AcqMchInfoPo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SimpleTest {
    @Autowired
    private AcqMchInfoMapper acqMchInfoMapper;

    @Test
    public void select(){
        List<AcqMchInfoPo> list=acqMchInfoMapper.selectList(null);
        list.forEach(System.out::println);
    }
}
