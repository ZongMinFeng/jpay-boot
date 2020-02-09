package com.jpay.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpay.common.exception.JpayBootException;
import com.jpay.common.util.SnowFlake;
import com.jpay.system.config.Constant;
import com.jpay.system.config.TxnId;
import com.jpay.system.mapper.*;
import com.jpay.system.pojo.bo.*;
import com.jpay.system.pojo.panto.ChargeTo;
import com.jpay.system.pojo.panto.RefundTo;
import com.jpay.system.pojo.panto.SaleTo;
import com.jpay.system.pojo.po.*;
import com.jpay.system.pojo.vo.MemberDetailsQueryByConVo;
import com.jpay.system.pojo.vo.MemberQueryByConVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MemberUserService {
    @Autowired
    private UserInfoMapper memUserMapper;

    @Resource
    UserMemberMapper memUserIssuMapper;

    @Autowired
    private AccountService accountService;

    @Resource
    private SysInstMapper uInstMapper;

    @Resource
    private SysAccTypeMapper sysCardTypeMapper;

    @Resource
    private AcqTransMapper transMapper;

    @Resource
    private UserAccountMapper panMapper;

    @Resource
    private AcqVoucherMapper acqVoucherMapper;

    @Resource
    private AcqVoucherDetailMapper acqVoucherDetailMapper;

    @Transactional
    public void memberSave(MemberSaveBo memberSaveBo) {
        UserInfoPo memUserPo=new UserInfoPo();
        memUserPo.setUserId(SnowFlake.nextId()+"");
        memUserPo.setUserName(memberSaveBo.getName());
        memUserPo.setPhone(memberSaveBo.getPhone());
        memUserPo.setCreateDate(new Date());
        memUserPo.setCreateTellerId(memberSaveBo.getCreateTellerId());
        memUserPo.setModiDate(memUserPo.getCreateDate());
        memUserPo.setModiTellerId(memUserPo.getCreateTellerId());
        try {
            memUserMapper.insert(memUserPo);
        }catch (DataAccessException e){
            //如果会员名和手机号相同，直接新增对应关系。如果手机号存在，会员名不同，报会员已经存在错误。
            UserInfoPo memUserPoCheck=memUserMapper.queryByPhone(memUserPo.getPhone());
            if (memUserPoCheck==null||!memUserPoCheck.getUserName().equals(memUserPo.getUserName())){
                throw new JpayBootException("会员已经存在");
            }
            //如果存在
            memUserPo.setUserId(memUserPoCheck.getUserId());
        }

        //新增mem_user_issu对应关系
        UserMemberPo memUserIssuPo=new UserMemberPo();
        memUserIssuPo.setMemId(SnowFlake.nextId()+"");
        memUserIssuPo.setUserId(memUserPo.getUserId());
        memUserIssuPo.setIssuId(memberSaveBo.getIssuId());
        memUserIssuPo.setCreateDate(memUserPo.getCreateDate());
        memUserIssuPo.setCreateTellerId(memUserPo.getCreateTellerId());
        memUserIssuPo.setModiDate(memUserPo.getModiDate());
        memUserIssuPo.setModiTellerId(memUserPo.getModiTellerId());
        int insert = memUserIssuMapper.insert(memUserIssuPo);
        if (insert!=1){
            throw new JpayBootException("新增会员失败");
        }

        //添加主卡号
        //准备发卡机构信息
        SysInstPo uInstPo=uInstMapper.selectById(memberSaveBo.getIssuId());
        //准备卡类型信息
        SysAccTypePo sysCardTypePo=sysCardTypeMapper.queryByIssuIdAndAccOrder(memberSaveBo.getIssuId(), "00");
        String pan=accountService.cardSave(uInstPo, sysCardTypePo, memUserIssuPo, 0.00, memUserPo.getCreateTellerId());

        //记录trans
        AcqTransPo transPo=new AcqTransPo();
        transPo.setTransId(SnowFlake.nextId()+"");
        transPo.setIssuId(memberSaveBo.getIssuId());
        transPo.setAcqId(memberSaveBo.getAcqId());
        transPo.setAccount(pan);
        transPo.setTxnId(TxnId.OPEN);
        transPo.setAmt(0.00);
        transPo.setRefundAmt(0.00);
        transPo.setBal(0.00);
        transPo.setMid(memberSaveBo.getMch());
        transPo.setMchName(memberSaveBo.getMchName());
        transPo.setSettleStatus(0);
        transPo.setCreateDate(new Date());
        transPo.setCreateTellerId(memberSaveBo.getCreateTellerId());
        transPo.setModiDate(transPo.getCreateDate());
        transPo.setModiTellerId(transPo.getCreateTellerId());
        insert = transMapper.insert(transPo);
        if (insert!=1){
            throw new JpayBootException("新增会员失败");
        }

        //添加返利卡号
        //准备卡类型信息
        SysAccTypePo sysCardTypePoFl=sysCardTypeMapper.queryByIssuIdAndAccOrder(memberSaveBo.getIssuId(), "01");
        String panFl=accountService.cardSave(uInstPo, sysCardTypePoFl, memUserIssuPo, 0.00, memUserPo.getCreateTellerId());

        //记录trans
        AcqTransPo transPoFl=new AcqTransPo();
        transPoFl.setTransId(SnowFlake.nextId()+"");
        transPoFl.setIssuId(memberSaveBo.getIssuId());
        transPoFl.setAcqId(memberSaveBo.getAcqId());
        transPoFl.setAccount(panFl);
        transPoFl.setTxnId(TxnId.OPEN);
        transPoFl.setAmt(0.00);
        transPoFl.setRefundAmt(0.00);
        transPoFl.setBal(0.00);
        transPoFl.setMid(memberSaveBo.getMch());
        transPoFl.setMchName(memberSaveBo.getMchName());
        transPoFl.setSettleStatus(0);
        transPoFl.setCreateDate(new Date());
        transPoFl.setCreateTellerId(memberSaveBo.getCreateTellerId());
        transPoFl.setModiDate(transPo.getCreateDate());
        transPoFl.setModiTellerId(transPoFl.getCreateTellerId());
        insert = transMapper.insert(transPoFl);
        if (insert!=1){
            throw new JpayBootException("新增会员失败");
        }

        //更新对应关系
        UserMemberPo memUserIssuPoUpdate=new UserMemberPo();
        memUserIssuPoUpdate.setMemId(memUserIssuPo.getMemId());
        memUserIssuPoUpdate.setPrimaryPan(pan);
        memUserIssuPoUpdate.setPrimaryRebatePan(panFl);
        int i = memUserIssuMapper.updateById(memUserIssuPoUpdate);
        if (i!=1){
            throw new JpayBootException("新增会员失败");
        }

    }

    @Transactional
    public void memberDelete(String memId, String issuId) {
        memUserIssuMapper.deleteByMemIdAndIssuId(memId, issuId);
    }

    public MemberQueryByConVo memberQueryByCon(MemberQueryByConBo memberQueryByConBo) {
        QueryWrapper<UserMemberPo> userMemberPoQueryWrapper = new QueryWrapper<UserMemberPo>();
        userMemberPoQueryWrapper.eq("issu_id", memberQueryByConBo.getIssuId());
        Page<UserMemberPo> page=new Page<UserMemberPo>(memberQueryByConBo.getPage(), memberQueryByConBo.getPageSize());
        IPage<UserMemberPo> iPage=memUserIssuMapper.selectPage(page, userMemberPoQueryWrapper);
        List<UserMemberPo> memUserIssuPos=iPage.getRecords();
        List<UserInfoPo> memUserPos=new ArrayList<UserInfoPo>();
        for (UserMemberPo memUserIssuPo:memUserIssuPos){
            UserInfoPo memUserPo = memUserMapper.selectById(memUserIssuPo.getUserId());
            if (memUserPo!=null){
                memUserPos.add(memUserPo);
            }
        }
        MemberQueryByConVo memberQueryByConVo=new MemberQueryByConVo();
        memberQueryByConVo.setRows(memUserPos);

        //查询总条数
        Long num=iPage.getTotal();
        memberQueryByConVo.setAllCount(num);
        return memberQueryByConVo;
    }

    public MemberDetailsQueryByConVo memberDetailsQueryByCon(MemberDetailsQueryByConBo memberDetails) {
        //获取会员信息
        UserInfoPo memUserPo;
        if (memberDetails.getUserId()!=null){
            memUserPo=memUserMapper.selectById(memberDetails.getUserId());
            if (memUserPo==null){
                throw new JpayBootException("查找会员失败");
            }
        }else{
            //通过手机号查找
            memUserPo=memUserMapper.queryByPhone(memberDetails.getPhone());
            if (memUserPo==null){
                throw new JpayBootException("查找会员失败");
            }
        }

        //查找会员发卡机构对应关系
        UserMemberPo memUserIssuPo=memUserIssuMapper.queryByMemIdANDIssuId(memUserPo.getUserId(), memberDetails.getIssuId());

        System.out.println(memberDetails.toString());//debug

        //查找主卡号
        UserAccountPo panPo=panMapper.selectById(memUserIssuPo.getPrimaryPan());

        //查找主返利卡号
        UserAccountPo panPoFl=panMapper.selectById(memUserIssuPo.getPrimaryRebatePan());

        MemberDetailsQueryByConVo memberDetailsVo=new MemberDetailsQueryByConVo();
        memberDetailsVo.setMemId(memUserPo.getUserId());
        memberDetailsVo.setUserId(memUserIssuPo.getUserId());
        memberDetailsVo.setIssuId(memUserIssuPo.getIssuId());
        memberDetailsVo.setName(memUserPo.getUserName());
        memberDetailsVo.setPhone(memUserPo.getPhone());
        memberDetailsVo.setBal(panPo.getCurrAmt()+panPoFl.getCurrAmt());
        return memberDetailsVo;
    }

    /**
     * 2.26	充值
     * @param memberChargeBo
     */
    @Transactional
    public void memberCharge(MemberChargeBo memberChargeBo) {
        UserMemberPo memUserIssuPo=memUserIssuMapper.queryByMemIdANDIssuId(memberChargeBo.getMemId(), memberChargeBo.getIssuId());
        if (memUserIssuPo==null){
            throw new JpayBootException("会员不存在");
        }
        ChargeTo chargeTo=new ChargeTo();
        chargeTo.setTransId(SnowFlake.nextId()+"");
        chargeTo.setIssuId(memberChargeBo.getIssuId());
        chargeTo.setAcqId(memberChargeBo.getAcqId());
        chargeTo.setPan(memUserIssuPo.getPrimaryPan());
        chargeTo.setAmt(memberChargeBo.getAmt());
        chargeTo.setMch(memberChargeBo.getMch());
        chargeTo.setMchName(memberChargeBo.getMchName());
        chargeTo.setCreateDate(new Date());
        chargeTo.setCreateTellerId(memberChargeBo.getCreateTellerId());

        //充值
        Double bal=accountService.charge(chargeTo);

        //记录mem_trans表
        AcqVoucherPo memTransPo=new AcqVoucherPo();
        memTransPo.setVoucher(memberChargeBo.getTransId());
        memTransPo.setMemId(memberChargeBo.getMemId());
        memTransPo.setTxnId(TxnId.CHARGE);
        memTransPo.setAmt(memberChargeBo.getAmt());
        memTransPo.setBal(bal);
        memTransPo.setCreateDate(chargeTo.getCreateDate());
        memTransPo.setModiDate(chargeTo.getCreateDate());
        memTransPo.setTxnName(TxnId.CHARGE_NAME);
        memTransPo.setMid(chargeTo.getMch());
        memTransPo.setMchName(chargeTo.getMchName());
        memTransPo.setIssuId(memberChargeBo.getIssuId());
        memTransPo.setAcqId(memberChargeBo.getAcqId());
        memTransPo.setRefundAmt(0.00);
        memTransPo.setRefundStatus(Constant.REFUND_STATUS_0);
        int insert1 = acqVoucherMapper.insert(memTransPo);
        if (insert1!=1){
            throw new JpayBootException("卡片充值失败");
        }

        //记录mem_trans_detail表
        AcqVoucherDetailPo memTransDetailPo=new AcqVoucherDetailPo();
        memTransDetailPo.setTransId(chargeTo.getTransId());
        memTransDetailPo.setVoucher(memberChargeBo.getTransId());
        memTransDetailPo.setAccount(memUserIssuPo.getPrimaryPan());
        memTransDetailPo.setAmt(chargeTo.getAmt());
        memTransDetailPo.setRefundAmt(0.00);
        int insert = acqVoucherDetailMapper.insert(memTransDetailPo);
        if (insert!=1){
            throw new JpayBootException("卡片充值失败");
        }
    }

    @Transactional
    public void memberSale(MemberSaleBo memberSaleBo) {
        UserMemberPo memUserIssuPo=memUserIssuMapper.queryByMemIdANDIssuId(memberSaleBo.getMemId(), memberSaleBo.getIssuId());
        if (memUserIssuPo==null){
            throw new JpayBootException("会员不存在");
        }
        SaleTo saleTo=new SaleTo();
        saleTo.setTransId(SnowFlake.nextId()+"");
        saleTo.setIssuId(memberSaleBo.getIssuId());
        saleTo.setAcqId(memberSaleBo.getAcqId());
        saleTo.setPan(memUserIssuPo.getPrimaryPan());
        saleTo.setAmt(memberSaleBo.getAmt());
        saleTo.setMch(memberSaleBo.getMch());
        saleTo.setMchName(memberSaleBo.getMchName());
        saleTo.setCreateDate(new Date());
        saleTo.setCreateTellerId(memberSaleBo.getCreateTellerId());

        //消费
        UserAccountPo panPo=panMapper.selectById(memUserIssuPo.getPrimaryPan());
        Double bal=accountService.sale(saleTo);
        if(panPo==null){
            throw new JpayBootException("消费失败");
        }
        if(panPo.getCurrAmt()-memberSaleBo.getAmt()<0.005){
            throw new JpayBootException("余额不足");
        }

        //记录mem_trans表
        AcqVoucherPo memTransPo=new AcqVoucherPo();
        memTransPo.setVoucher(memberSaleBo.getTransId());
        memTransPo.setMemId(memberSaleBo.getMemId());
        memTransPo.setTxnId(TxnId.SALE);
        memTransPo.setTxnName(TxnId.SALE_NAME);
        memTransPo.setAmt(memberSaleBo.getAmt());
        memTransPo.setBal(bal);
        memTransPo.setCreateDate(saleTo.getCreateDate());
        memTransPo.setModiDate(saleTo.getCreateDate());
        memTransPo.setMid(memberSaleBo.getMch());
        memTransPo.setMchName(memberSaleBo.getMchName());
        memTransPo.setIssuId(memberSaleBo.getIssuId());
        memTransPo.setAcqId(memberSaleBo.getAcqId());
        memTransPo.setRefundAmt(0.00);
        memTransPo.setRefundStatus(Constant.REFUND_STATUS_0);
        int insert1 = acqVoucherMapper.insert(memTransPo);
        if (insert1!=1){
            throw new JpayBootException("卡片充值失败");
        }

        //记录mem_trans_detail表
        AcqVoucherDetailPo memTransDetailPo=new AcqVoucherDetailPo();
        memTransDetailPo.setTransId(saleTo.getTransId());
        memTransDetailPo.setVoucher(memberSaleBo.getTransId());
        memTransDetailPo.setAccount(memUserIssuPo.getPrimaryPan());
        memTransDetailPo.setAmt(memberSaleBo.getAmt());
        memTransDetailPo.setRefundAmt(0.00);
        int insert = acqVoucherDetailMapper.insert(memTransDetailPo);
        if (insert!=1){
            throw new JpayBootException("卡片充值失败");
        }

    }


    /**
     * 2.28	 会员退货
     * @param memberRefundBo
     */
    @Transactional
    public void memberRefund(MemberRefundBo memberRefundBo) {
        AcqVoucherPo orgAcqVoucherPo = acqVoucherMapper.selectById(memberRefundBo.getOrgVoucher());
        if (orgAcqVoucherPo==null){
            throw new JpayBootException("无此订单");
        }

        //判断退货金额
        if(orgAcqVoucherPo.getAmt()-orgAcqVoucherPo.getRefundAmt()-memberRefundBo.getRefoundAmt()<-0.05){
            throw new JpayBootException("无效金额");
        }

        //获取订单详单
        List<AcqVoucherDetailPo> acqVoucherDetailPos = acqVoucherDetailMapper.queryByVoucher(memberRefundBo.getOrgVoucher());

        //分配退货金额
        double refundAmt=memberRefundBo.getRefoundAmt();
        List<RefundTo> refundTos=new ArrayList<RefundTo>();
        Date now=new Date();
        for (AcqVoucherDetailPo acqVoucherDetailPo:acqVoucherDetailPos){
            double refundAmtOnce=acqVoucherDetailPo.getAmt()-acqVoucherDetailPo.getRefundAmt();
            //已经退货的继续
            if (refundAmtOnce<0.005){
                continue;
            }

            RefundTo refundTo=new RefundTo();
            refundTo.setIssuId(memberRefundBo.getIssuId());
            refundTo.setAcqId(memberRefundBo.getAcqId());
            refundTo.setAccount(acqVoucherDetailPo.getAccount());
            refundTo.setTransId(SnowFlake.nextId()+"");
            refundTo.setOrgTransId(acqVoucherDetailPo.getTransId());
            refundTo.setMid(memberRefundBo.getMid());
            refundTo.setMchName(memberRefundBo.getMchName());
            refundTo.setPid(memberRefundBo.getPid());
            refundTo.setPosName(memberRefundBo.getPosName());
            refundTo.setCreateDate(now);
            refundTo.setCreateTellerId(memberRefundBo.getCreateTellerId());

            //完成金额分配,结束
            if (refundAmtOnce-refundAmt>-0.005){
                refundTo.setRefundAmt(refundAmt);
                refundTos.add(refundTo);
                break;
            }

            //这笔全部退货，下笔继续分配
            refundTo.setRefundAmt(refundAmtOnce);
            refundTos.add(refundTo);
            refundAmt -= refundAmtOnce;
        }

        //分别退货
        for (RefundTo refundTo:refundTos){
            refundTo.setBal(accountService.refund(refundTo));
            //更新原交易detail记录
            Integer integer = acqVoucherDetailMapper.updateRefundAmt(refundTo.getOrgTransId(), refundTo.getRefundAmt());
            if (integer!=1){
                throw new JpayBootException("会员退货失败");
            }

            //记录退货交易acq_voucher_detail表
            AcqVoucherDetailPo acqVoucherDetailPo=new AcqVoucherDetailPo();
            acqVoucherDetailPo.setTransId(refundTo.getTransId());
            acqVoucherDetailPo.setVoucher(memberRefundBo.getVoucher());
            acqVoucherDetailPo.setAccount(refundTo.getAccount());
            acqVoucherDetailPo.setAmt(refundTo.getRefundAmt());
            acqVoucherDetailPo.setRefundAmt(0.00);
            acqVoucherDetailPo.setOrgTransId(refundTo.getOrgTransId());
            int insert = acqVoucherDetailMapper.insert(acqVoucherDetailPo);
            if (insert!=1){
                throw new JpayBootException("会员退货失败");
            }
        }

        //更新原交易acq_voucher表
        //判断是否全部退货
        Integer refundStatus=1;
        if (orgAcqVoucherPo.getRefundAmt()+memberRefundBo.getRefoundAmt()-orgAcqVoucherPo.getAmt()<0.005){
            //全部退货
            refundStatus=2;
        }
        Integer integer = acqVoucherMapper.updateRefundAmt(memberRefundBo.getOrgVoucher(), memberRefundBo.getRefoundAmt(), refundStatus, now);
        if (integer!=1){
            throw new JpayBootException("会员退货失败");
        }

        //记录退货交易acq_voucher表
        AcqVoucherPo acqVoucherPo=new AcqVoucherPo();
        acqVoucherPo.setVoucher(memberRefundBo.getVoucher());
        acqVoucherPo.setTxnId(TxnId.REFOUND);
        acqVoucherPo.setTxnName(TxnId.REFOUND_NAME);
        acqVoucherPo.setMid(memberRefundBo.getMid());
        acqVoucherPo.setMchName(memberRefundBo.getMchName());
        acqVoucherPo.setPid(memberRefundBo.getPid());
        acqVoucherPo.setPosName(memberRefundBo.getPosName());
        acqVoucherPo.setMemId(memberRefundBo.getMemId());
        acqVoucherPo.setIssuId(memberRefundBo.getIssuId());
        acqVoucherPo.setAcqId(memberRefundBo.getAcqId());
        acqVoucherPo.setAmt(memberRefundBo.getRefoundAmt());
        acqVoucherPo.setBal(refundTos.get(refundTos.size()-1).getBal());
        acqVoucherPo.setRefundAmt(0.00);
        acqVoucherPo.setRefundStatus(0);
        acqVoucherPo.setCreateDate(now);
        acqVoucherPo.setModiDate(now);

        integer = acqVoucherMapper.insert(acqVoucherPo);
        if (integer!=1){
            throw new JpayBootException("会员退货失败");
        }
    }
}
