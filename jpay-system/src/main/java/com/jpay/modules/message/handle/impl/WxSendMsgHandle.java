package com.jpay.modules.message.handle.impl;

import com.jpay.modules.message.handle.ISendMsgHandle;

public class WxSendMsgHandle implements ISendMsgHandle {

	@Override
	public void SendMsg(String es_receiver, String es_title, String es_content) {
		// TODO Auto-generated method stub
		System.out.println("发微信消息模板");
	}

}
