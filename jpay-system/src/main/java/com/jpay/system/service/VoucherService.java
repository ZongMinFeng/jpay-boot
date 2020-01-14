package com.jpay.system.service;

import com.github.pagehelper.PageHelper;
import com.jpay.system.mapper.AcqVoucherMapper;
import com.jpay.system.pojo.bo.VoucherQueryBo;
import com.jpay.system.pojo.po.AcqVoucherPo;
import com.jpay.system.pojo.vo.VoucherQueryVo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VoucherService {
    @Resource
    AcqVoucherMapper acqVoucherMapper;

    public VoucherQueryVo voucherQuery(VoucherQueryBo voucherQueryBo) {
        PageHelper.startPage(voucherQueryBo.getPage(), voucherQueryBo.getPageSize());
        Example example=new Example(AcqVoucherPo.class);
        example.setOrderByClause("create_date desc");
        Example.Criteria criteria=example.createCriteria();
        criteria.andEqualTo("issuId", voucherQueryBo.getIssuId());
        criteria.andEqualTo("acqId", voucherQueryBo.getAcqId());
        criteria.andEqualTo("memId", voucherQueryBo.getMemId());
        criteria.andEqualTo("account", voucherQueryBo.getAccount());

        List<AcqVoucherPo> acqVoucherPos = acqVoucherMapper.selectByExample(example);
        VoucherQueryVo voucherQueryVo=new VoucherQueryVo();
        voucherQueryVo.setRows(acqVoucherPos);

        //查询总条数
        Integer allCount=acqVoucherMapper.queryTotalNumByMemId(voucherQueryBo.getIssuId(), voucherQueryBo.getAcqId(), voucherQueryBo.getMemId());
        voucherQueryVo.setAllCount(allCount);
        return voucherQueryVo;
    }
}
