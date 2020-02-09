package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.AcqMchInfoPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface AcqMchInfoMapper extends BaseMapper<AcqMchInfoPo> {
    @Select("SELECT count(*) FROM acq_mch_info WHERE acq_id=#{acqId}")
    Integer queryTotalNum(@Param("acqId") String acqId);
}
