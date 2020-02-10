package com.jpay.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpay.system.mapper.AcqVoucherMapper;
import com.jpay.system.pojo.bo.VoucherQueryBo;
import com.jpay.system.pojo.po.AcqVoucherPo;
import com.jpay.system.pojo.vo.VoucherQueryVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class VoucherService {
    @Resource
    AcqVoucherMapper acqVoucherMapper;

    public VoucherQueryVo voucherQuery(VoucherQueryBo voucherQueryBo) {
        System.out.println("voucherQueryBo:"+voucherQueryBo);//debug
        QueryWrapper<AcqVoucherPo> acqVoucherPoQueryWrapper = new QueryWrapper<>();
        acqVoucherPoQueryWrapper.eq("issu_id", voucherQueryBo.getIssuId());
        acqVoucherPoQueryWrapper.eq("acq_id", voucherQueryBo.getAcqId());
        acqVoucherPoQueryWrapper.eq("mem_id", voucherQueryBo.getMemId());
        acqVoucherPoQueryWrapper.eq(voucherQueryBo.getAccount()!=null,"account", voucherQueryBo.getAccount());
        acqVoucherPoQueryWrapper.orderByDesc("create_date");
        Page<AcqVoucherPo> page=new Page<>(voucherQueryBo.getPage(), voucherQueryBo.getPageSize());
        IPage<AcqVoucherPo> iPage=acqVoucherMapper.selectPage(page, acqVoucherPoQueryWrapper);
        List<AcqVoucherPo> acqVoucherPos = iPage.getRecords();
        VoucherQueryVo voucherQueryVo=new VoucherQueryVo();
        voucherQueryVo.setRows(acqVoucherPos);

        //查询总条数
        Long allCount=iPage.getTotal();
        voucherQueryVo.setAllCount(allCount);
        return voucherQueryVo;
    }
}
