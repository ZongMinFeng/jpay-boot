package com.jpay.system.controller;

import com.jpay.common.pojo.vo.Result;
import com.jpay.system.pojo.bo.VoucherQueryBo;
import com.jpay.system.pojo.vo.VoucherQueryVo;
import com.jpay.system.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class VoucherController {
    @Autowired
    VoucherService voucherService;

    @RequestMapping("voucherQuery")
    public Result<VoucherQueryVo> voucherQuery(@RequestBody VoucherQueryBo voucherQueryBo){
        Result<VoucherQueryVo> result=new Result<VoucherQueryVo>();
        VoucherQueryVo voucherQueryVo=voucherService.voucherQuery(voucherQueryBo);
        result.setResult(voucherQueryVo);
        return result;
    }
}
