package com.jpay.system.service;

import com.jpay.common.exception.JpayBootException;
import com.jpay.system.config.Constant;
import com.jpay.system.config.TxnId;
import com.jpay.system.mapper.AcqTransMapper;
import com.jpay.system.mapper.UserAccountMapper;
import com.jpay.system.pojo.panto.ChargeTo;
import com.jpay.system.pojo.panto.RefundTo;
import com.jpay.system.pojo.panto.SaleTo;
import com.jpay.system.pojo.po.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AccountService {
    @Autowired
    private SysSequenceService sysSequenceService;

    @Resource
    private UserAccountMapper panMapper;

    @Resource
    private AcqTransMapper acqTransMapper;

    public String cardSave(SysInstPo uInstPo, SysAccTypePo sysCardTypePo, UserMemberPo memUserIssuPo, Double amt, String createTellerId){
        //卡号规则 "8"+3位发卡机构序号+2位卡类型序号+9位卡序号+1位校验码
        String pan="8"+uInstPo.getIssuCode()+sysCardTypePo.getAccOrder()+nextCardSeq(sysCardTypePo.getAccType());
        char checkNum=getBankCardCheckCode(pan);
        pan=pan+checkNum;
        UserAccountPo panPo=new UserAccountPo();
        panPo.setAccount(pan);
        panPo.setIssuId(uInstPo.getInstId());
        if (memUserIssuPo!=null){
            panPo.setMemId(memUserIssuPo.getMemId());
        }
        panPo.setAccType(sysCardTypePo.getAccType());
        panPo.setCurrAmt(amt);
        panPo.setLastAmt(amt);
        panPo.setFaceAmt(sysCardTypePo.getFaceAmt());
        panPo.setCreateDate(new Date());
        panPo.setCreateTellerId(createTellerId);
        panPo.setModiDate(panPo.getCreateDate());
        panPo.setModiTellerId(createTellerId);
        int insert = panMapper.insert(panPo);
        if (insert!=1){
            throw new JpayBootException("保存卡号失败");
        }
        return pan;
    }

    private String nextCardSeq(String cardTypeId){
        Long nextVal=sysSequenceService.nextVal(cardTypeId);
        if (nextVal>999999999){
            throw new JpayBootException("序列超限");
        }
        return String.format("%09d", nextVal);
    }

    /**
     * Luhn算法 检查校验位
     * @param digits
     * @return
     */
    private static boolean check(int[] digits) {
        int sum = 0;
        int length = digits.length;
        for (int i = 0; i < length; i++) {

            // get digits in reverse order
            int digit = digits[length - i - 1];

            // every 2nd number multiply with 2
            if (i % 2 == 1) {
                digit *= 2;
            }
            sum += digit > 9 ? digit - 9 : digit;
        }
        return sum % 10 == 0;
    }

    public static char getBankCardCheckCode(String nonCheckCodeCardId){
        if(nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0|| !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for(int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if(j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char)((10 - luhmSum % 10) + '0');
    }

    /**
     * 充值
     * @param chargeTo
     * @return 余额
     */
    public Double charge(ChargeTo chargeTo) {
        //加钱
        Integer integer = panMapper.updateAmt(chargeTo.getAmt(), chargeTo.getPan());
        if (integer!=1){
            throw new JpayBootException("卡片充值失败");
        }

        //查询余额
        UserAccountPo panPo=panMapper.selectByPrimaryKey(chargeTo.getPan());
        if (panPo==null){
            throw new JpayBootException("卡片不存在");
        }

        //记录Trans表
        AcqTransPo transPo=new AcqTransPo();
        transPo.setTransId(chargeTo.getTransId());
        transPo.setTxnId(TxnId.CHARGE);
        transPo.setIssuId(chargeTo.getIssuId());
        transPo.setAcqId(chargeTo.getAcqId());
        transPo.setAccount(chargeTo.getPan());
        transPo.setAmt(chargeTo.getAmt());
        transPo.setRefundAmt(0.00);
        transPo.setBal(panPo.getCurrAmt());
        transPo.setMid(chargeTo.getMch());
        transPo.setMchName(chargeTo.getMchName());
        transPo.setPid(chargeTo.getPos());
        transPo.setPosName(chargeTo.getPosName());
        transPo.setSettleStatus(Constant.SETTLE_STATUS0);
        if (chargeTo.getCreateDate()==null){
            transPo.setCreateDate(new Date());
        }else{
            transPo.setCreateDate(chargeTo.getCreateDate());
        }
        transPo.setCreateTellerId(chargeTo.getCreateTellerId());
        transPo.setModiDate(transPo.getCreateDate());
        transPo.setModiTellerId(transPo.getCreateTellerId());

        int insert = acqTransMapper.insert(transPo);
        if (insert!=1){
            throw new JpayBootException("卡片充值失败");
        }

        return transPo.getBal();
    }

    /**
     *3.2	账户消费
     * @param saleTo 消费结构体
     * @return 余额
     */
    public Double sale(SaleTo saleTo){
        //减钱
        Integer integer = panMapper.updateAmt(-saleTo.getAmt(), saleTo.getPan());
        if (integer!=1){
            throw new JpayBootException("卡片充值失败");
        }
        //查询余额
        UserAccountPo panPo=panMapper.selectByPrimaryKey(saleTo.getPan());
        if (panPo==null){
            throw new JpayBootException("卡片不存在");
        }

        if(panPo.getCurrAmt()<0.005){
            throw new JpayBootException("余额不足");
        }

        //记录Trans表
        AcqTransPo transPo=new AcqTransPo();
        transPo.setTransId(saleTo.getTransId());
        transPo.setTxnId(TxnId.SALE);
        transPo.setIssuId(saleTo.getIssuId());
        transPo.setAcqId(saleTo.getAcqId());
        transPo.setAccount(saleTo.getPan());
        transPo.setAmt(saleTo.getAmt());
        transPo.setRefundAmt(0.00);
        transPo.setBal(panPo.getCurrAmt());
        transPo.setMid(saleTo.getMch());
        transPo.setMchName(saleTo.getMchName());
        transPo.setPid(saleTo.getPos());
        transPo.setPosName(saleTo.getPosName());
        transPo.setSettleStatus(Constant.SETTLE_STATUS0);
        if (saleTo.getCreateDate()==null){
            transPo.setCreateDate(new Date());
        }else{
            transPo.setCreateDate(saleTo.getCreateDate());
        }
        transPo.setCreateTellerId(saleTo.getCreateTellerId());
        transPo.setModiDate(transPo.getCreateDate());
        transPo.setModiTellerId(transPo.getCreateTellerId());

        int insert = acqTransMapper.insert(transPo);
        if (insert!=1){
            throw new JpayBootException("卡片充值失败");
        }

        return transPo.getBal();
    }

    /**
     * 3.3	账户退货
     * @param refundTo
     * @return
     */
    public Double refund(RefundTo refundTo){
        //查找原交易
        AcqTransPo acqTransPo = acqTransMapper.selectByPrimaryKey(refundTo.getOrgTransId());
        if (acqTransPo==null){
            throw new JpayBootException("无此交易");
        }

        //因素检查
        //发卡机构
        if (!acqTransPo.getIssuId().equals(refundTo.getIssuId())){
            throw new JpayBootException("无此交易");
        }
        //收单机构
        if (!acqTransPo.getAcqId().equals(refundTo.getAcqId())){
            throw new JpayBootException("无此交易");
        }
        //商户
        if (!acqTransPo.getMid().equals(refundTo.getMid())){
            throw new JpayBootException("无此交易");
        }
        //账号
        if (!acqTransPo.getAccount().equals(refundTo.getAccount())){
            throw new JpayBootException("无此交易");
        }
        //退货金额
        if (acqTransPo.getAmt()-acqTransPo.getRefundAmt()-refundTo.getRefundAmt()<-0.005){
            throw new JpayBootException("无效金额");
        }

        //加钱
        Integer integer = panMapper.updateAmt(refundTo.getRefundAmt(), refundTo.getAccount());
        if (integer!=1){
            throw new JpayBootException("账户退货失败");
        }

        //查询余额
        UserAccountPo accountPo=panMapper.selectByPrimaryKey(refundTo.getAccount());
        if (accountPo==null){
            throw new JpayBootException("卡片不存在");
        }

        //更新原交易记录退货金额、修改时间、修改者
        integer = acqTransMapper.updateRefundAmt(refundTo.getRefundAmt(), refundTo.getOrgTransId(), refundTo.getCreateDate(), refundTo.getCreateTellerId());
        if (integer!=1){
            throw new JpayBootException("账户退货失败");
        }

        //记录Trans表
        AcqTransPo transPo=new AcqTransPo();
        transPo.setTransId(refundTo.getTransId());
        transPo.setTxnId(TxnId.REFOUND);
        transPo.setTxnIdName(TxnId.REFOUND_NAME);
        transPo.setIssuId(refundTo.getIssuId());
        transPo.setAcqId(refundTo.getAcqId());
        transPo.setAccount(refundTo.getAccount());
        transPo.setAmt(refundTo.getRefundAmt());
        transPo.setRefundAmt(0.00);
        transPo.setBal(accountPo.getCurrAmt());
        transPo.setMid(refundTo.getMid());
        transPo.setMchName(refundTo.getMchName());
        transPo.setPid(refundTo.getPid());
        transPo.setPosName(refundTo.getPosName());
        transPo.setSettleStatus(Constant.SETTLE_STATUS0);
        if (refundTo.getCreateDate()==null){
            transPo.setCreateDate(new Date());
        }else{
            transPo.setCreateDate(refundTo.getCreateDate());
        }
        transPo.setCreateTellerId(refundTo.getCreateTellerId());
        transPo.setModiDate(transPo.getCreateDate());
        transPo.setModiTellerId(transPo.getCreateTellerId());

        int insert = acqTransMapper.insert(transPo);
        if (insert!=1){
            throw new JpayBootException("账户退货失败");
        }

        return transPo.getBal();
    }

}
