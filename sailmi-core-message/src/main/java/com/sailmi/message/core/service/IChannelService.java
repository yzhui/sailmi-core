package com.sailmi.message.core.service;

import com.sailmi.message.core.dao.entity.Message;
import com.sailmi.message.core.dao.entity.MessageSetting;
import com.sailmi.message.core.dao.entity.Template;
import com.sailmi.message.core.exception.ChannelException;
import com.sailmi.message.core.model.dto.MessageDTO;
import com.sailmi.message.core.model.dto.QuerySendResult;
import com.sailmi.message.core.model.dto.SendMessageResult;

public interface IChannelService {

	/**
	 * 调用具体的短信平台发送短信
	 * @param messageSetting
	 * @param template
	 * @param message
	 * @return
	 */
	SendMessageResult send(MessageSetting messageSetting, Template template, MessageDTO message) throws ChannelException;

	/**
	 * 查询发送状态
	 * @param messageSetting
	 * @param message
	 * @return
	 */
	QuerySendResult querySendStatus(MessageSetting messageSetting, Message message);

	/**
	 * 调用具体的短信平台批量发送短信
	 * @param messageSetting
	 * @param mobile
	 * @param content
	 * @return
	 */
	SendMessageResult batchSend(MessageSetting messageSetting, String[] mobile, String content) throws ChannelException;

	/**
	 * 获取短信渠道名称
	 * @return
	 */
	String getChannel();

}
