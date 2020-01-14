package com.jpay.system.mapper;

import com.jpay.system.pojo.po.AcqPosInfoPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface AcqPosInfoMapper extends Mapper<AcqPosInfoPo> {
    @Select("SELECT count(*) FROM acq_pos_info WHERE acq_id=#{acqId}")
    Integer queryTotalNumByAcqId(@Param("acqId") String acqId);

    @Select("SELECT count(*) FROM acq_pos_info WHERE mid=#{mid}")
    Integer queryTotalNumByMid(@Param("mid") String mid);
}
