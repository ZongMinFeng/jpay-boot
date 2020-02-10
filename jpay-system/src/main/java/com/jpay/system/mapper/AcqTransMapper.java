package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.AcqTransPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import java.util.Date;

public interface AcqTransMapper extends BaseMapper<AcqTransPo> {
    @Update("UPDATE acq_trans SET refund_amt=refund_amt+#{refundAmt}, modi_date=#{modiDate}, modi_teller_id=#{modiTellerId} WHERE trans_id=#{transId}")
    Integer updateRefundAmt(@Param("refundAmt") Double amt, @Param("transId") String transId, @Param("modiDate")Date modiDate, @Param("modiTellerId") String modiTellerId);
}
