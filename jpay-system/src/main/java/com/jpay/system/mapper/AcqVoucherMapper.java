package com.jpay.system.mapper;

import com.jpay.system.pojo.po.AcqVoucherPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;

public interface AcqVoucherMapper extends Mapper<AcqVoucherPo> {
    @Select("SELECT count(*) FROM acq_voucher WHERE issu_id=#{issuId} AND acq_id=#{acqId} AND mem_id=#{memId}")
    Integer queryTotalNumByMemId(@Param("issuId") String issuId, @Param("acqId") String acqId, @Param("memId") String memId);

    /**
     *
     * @param voucher 订单号
     * @param refundAmt 退货金额
     * @param refundStatus 退货状态
     * @param now 修改时间
     * @return 更新状态
     */
    @Update("UPDATE acq_voucher SET refund_amt=refund_amt+#{refundAmt}, refund_status=#{refundStatus}, modi_date=#{now} WHERE voucher=#{voucher}")
    Integer updateRefundAmt(@Param("voucher") String voucher, @Param("refundAmt") Double refundAmt, @Param("refundStatus") Integer refundStatus, @Param("now")Date now);
}
