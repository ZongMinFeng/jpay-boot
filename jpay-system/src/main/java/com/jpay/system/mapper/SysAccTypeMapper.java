package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpay.system.pojo.po.SysAccTypePo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAccTypeMapper extends BaseMapper<SysAccTypePo> {
    @Select("SELECT count(*) FROM sys_acc_type WHERE issu_id=#{issuId}")
    Integer queryTotalNum(@Param("issuId") String issuId);

    @Select("SELECT acc_type as accType, acc_name as accName, issu_id as issuId, acc_order as accOrder, face_amt as faceAmt, " +
            "create_date as createDate, create_teller_id as createTellerId, modi_date as modiDate, modi_teller_id as modiTellerId " +
            "FROM sys_acc_type WHERE issu_id=#{issuId} AND acc_order=#{accOrder}")
    SysAccTypePo queryByIssuIdAndAccOrder(@Param("issuId") String issuId, @Param("accOrder") String issuOrder);
}
