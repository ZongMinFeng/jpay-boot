package com.jpay.system.mapper;

import com.jpay.system.pojo.po.AcqMchInfoPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface AcqMchInfoMapper extends Mapper<AcqMchInfoPo> {
    @Select("SELECT count(*) FROM acq_mch_info WHERE acq_id=#{acqId}")
    Integer queryTotalNum(@Param("acqId") String acqId);
}
