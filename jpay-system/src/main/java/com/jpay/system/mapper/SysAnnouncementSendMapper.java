package com.jpay.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpay.system.model.AnnouncementSendModel;
import com.jpay.system.pojo.po.SysAnnouncementSend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 用户通告阅读标记表
 * @Author: jeecg-boot
 * @Date:  2019-02-21
 * @Version: V1.0
 */
public interface SysAnnouncementSendMapper extends BaseMapper<SysAnnouncementSend> {

	public List<String> queryByUserId(@Param("userId") String userId);

	/**
	 * @功能：获取我的消息
	 * @param announcementSendModel
	 * @return
	 */
	public List<AnnouncementSendModel> getMyAnnouncementSendList(Page<AnnouncementSendModel> page, @Param("announcementSendModel") AnnouncementSendModel announcementSendModel);

}