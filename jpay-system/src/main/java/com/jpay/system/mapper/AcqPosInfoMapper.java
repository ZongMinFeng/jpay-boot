package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.AcqPosInfoPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface AcqPosInfoMapper extends BaseMapper<AcqPosInfoPo> {
    @Select("SELECT count(*) FROM acq_pos_info WHERE acq_id=#{acqId}")
    Integer queryTotalNumByAcqId(@Param("acqId") String acqId);

    @Select("SELECT count(*) FROM acq_pos_info WHERE mid=#{mid}")
    Integer queryTotalNumByMid(@Param("mid") String mid);
}
