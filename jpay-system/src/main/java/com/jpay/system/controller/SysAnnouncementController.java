package com.jpay.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpay.common.constant.CommonConstant;
import com.jpay.common.pojo.vo.Result;
import com.jpay.system.pojo.po.SysAnnouncement;
import com.jpay.system.pojo.po.SysAnnouncementSend;
import com.jpay.system.service.ISysAnnouncementSendService;
import com.jpay.system.service.ISysAnnouncementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @Title: Controller
 * @Description: 系统通告表
 * @Author: jeecg-boot
 * @Date: 2019-01-02
 * @Version: V1.0
 */
@RestController
@RequestMapping("/sys/annountCement")
@Slf4j
public class SysAnnouncementController {
	@Autowired
	private ISysAnnouncementService sysAnnouncementService;
	@Autowired
	private ISysAnnouncementSendService sysAnnouncementSendService;

	/**
	 * @功能：补充用户数据，并返回系统消息
	 * @return
	 */
	@RequestMapping(value = "/listByUser", method = RequestMethod.GET)
	public Result<Map<String,Object>> listByUser() {
		Result<Map<String,Object>> result = new Result<Map<String,Object>>();
//		LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
//		String userId = sysUser.getId();
		String userId = "system";//debug
		// 1.将系统消息补充到用户通告阅读标记表中
		Collection<String> anntIds = sysAnnouncementSendService.queryByUserId(userId);
		LambdaQueryWrapper<SysAnnouncement> querySaWrapper = new LambdaQueryWrapper<SysAnnouncement>();
		querySaWrapper.eq(SysAnnouncement::getMsgType, CommonConstant.MSG_TYPE_ALL); // 全部人员
		querySaWrapper.eq(SysAnnouncement::getDelFlag,CommonConstant.DEL_FLAG_0.toString());  // 未删除
		querySaWrapper.eq(SysAnnouncement::getSendStatus, CommonConstant.HAS_SEND); //已发布
//		querySaWrapper.ge(SysAnnouncement::getEndTime, sysUser.getCreateTime()); //新注册用户不看结束通知
		if(anntIds!=null&&anntIds.size()>0) {
			querySaWrapper.notIn(SysAnnouncement::getId, anntIds);
		}
		List<SysAnnouncement> announcements = sysAnnouncementService.list(querySaWrapper);
		if(announcements.size()>0) {
			for(int i=0;i<announcements.size();i++) {
				SysAnnouncementSend announcementSend = new SysAnnouncementSend();
				announcementSend.setAnntId(announcements.get(i).getId());
				announcementSend.setUserId(userId);
				announcementSend.setReadFlag(CommonConstant.NO_READ_FLAG);
				sysAnnouncementSendService.save(announcementSend);
			}
		}
		// 2.查询用户未读的系统消息
		Page<SysAnnouncement> anntMsgList = new Page<SysAnnouncement>(0,5);
		anntMsgList = sysAnnouncementService.querySysCementPageByUserId(anntMsgList,userId,"1");//通知公告消息
		Page<SysAnnouncement> sysMsgList = new Page<SysAnnouncement>(0,5);
		sysMsgList = sysAnnouncementService.querySysCementPageByUserId(sysMsgList,userId,"2");//系统消息
		Map<String,Object> sysMsgMap = new HashMap<String, Object>();
		sysMsgMap.put("sysMsgList", sysMsgList.getRecords());
		sysMsgMap.put("sysMsgTotal", sysMsgList.getTotal());
		sysMsgMap.put("anntMsgList", anntMsgList.getRecords());
		sysMsgMap.put("anntMsgTotal", anntMsgList.getTotal());
		result.setSuccess(true);
		result.setResult(sysMsgMap);
		return result;
	}

}
