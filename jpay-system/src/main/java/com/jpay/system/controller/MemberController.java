package com.jpay.system.controller;

import com.jpay.common.pojo.vo.Result;
import com.jpay.system.pojo.bo.*;
import com.jpay.system.pojo.vo.MemberDetailsQueryByConVo;
import com.jpay.system.pojo.vo.MemberQueryByConVo;
import com.jpay.system.service.MemberUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class MemberController {
    @Autowired
    private MemberUserService memberUserService;

    /**
     * 2.1.15	新增会员
     * @param memberSaveBo
     * @return
     */
    @RequestMapping("memberSave")
    public ResponseEntity<Void> memberSave(@RequestBody MemberSaveBo memberSaveBo){
        memberUserService.memberSave(memberSaveBo);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 2.1.16	删除会员
     * @param memberDeleteBo
     * @return
     */
    @RequestMapping("memberDelete")
    public ResponseEntity<Void> memberDelete(@RequestBody MemberDeleteBo memberDeleteBo){
        memberUserService.memberDelete(memberDeleteBo.getMemId(), memberDeleteBo.getIssuId());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    /**
     * 2.1.18	查询会员（分页查询）
     * @param memberQueryByConBo
     * @return
     */
    @RequestMapping("memberQueryByCon")
    public ResponseEntity<MemberQueryByConVo> memberQueryByCon(@RequestBody MemberQueryByConBo memberQueryByConBo){
        return ResponseEntity.ok(memberUserService.memberQueryByCon(memberQueryByConBo));
    }

    /**
     * 2.19	会员查询详细信息
     * @param memberDetailsQueryByConBo
     * @return
     */
    @RequestMapping("memberDetailsQueryByCon")
    public Result<MemberDetailsQueryByConVo> memberDetailsQueryByCon(@RequestBody MemberDetailsQueryByConBo memberDetailsQueryByConBo){
        Result<MemberDetailsQueryByConVo> result=new Result<MemberDetailsQueryByConVo>();
        MemberDetailsQueryByConVo memberDetailsQueryByConVo=memberUserService.memberDetailsQueryByCon(memberDetailsQueryByConBo);
        result.setResult(memberDetailsQueryByConVo);
        result.success("查询成功");
        return result;
    }

    /**
     * 2.26	 会员充值
     * @param memberChargeBo
     * @return
     */
    @RequestMapping("memberCharge")
    public Result<Void> memberCharge(@RequestBody MemberChargeBo memberChargeBo){
        Result<Void> result=new Result<Void>();
        memberUserService.memberCharge(memberChargeBo);
        return result;
    }

    /**
     * 2.27	 会员消费
     * @param memberSaleBo
     * @return
     */
    @RequestMapping("memberSale")
    public Result<Void> memberSale(@RequestBody MemberSaleBo memberSaleBo){
        Result<Void> result=new Result<Void>();
        memberUserService.memberSale(memberSaleBo);
        return result;
    }

    /**
     * 2.28	 会员退货
     * @param memberRefundBo
     * @return
     */
    @RequestMapping("memberRefund")
    public Result<Void> memberRefund(@RequestBody MemberRefundBo memberRefundBo){
        Result<Void> result=new Result<Void>();
        memberUserService.memberRefund(memberRefundBo);
        return result;
    }
}
