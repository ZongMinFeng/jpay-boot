package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.AcqVoucherDetailPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface AcqVoucherDetailMapper extends BaseMapper<AcqVoucherDetailPo> {
    @Select("SELECT trans_id as transId, voucher, account, amt, refund_amt as refundAmt FROM acq_voucher_detail WHERE voucher=#{voucher}")
    List<AcqVoucherDetailPo> queryByVoucher(@Param("voucher") String voucher);

    @Update("UPDATE acq_voucher_detail SET refund_amt=refund_amt+#{refundAmt} WHERE trans_id=#{transId}")
    public Integer updateRefundAmt(@Param("transId") String transId, @Param("refundAmt") Double refundAmt);
}
