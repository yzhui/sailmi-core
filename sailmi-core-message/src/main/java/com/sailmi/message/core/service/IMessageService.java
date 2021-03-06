package com.sailmi.message.core.service;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.core.mp.base.BaseService;
import com.sailmi.message.core.dao.entity.BatchMessage;
import com.sailmi.message.core.dao.entity.Message;
import com.sailmi.message.core.dao.entity.Template;
import com.sailmi.message.core.model.dto.BatchMessageDTO;
import com.sailmi.message.core.model.dto.MessageDTO;
import com.sailmi.message.core.model.dto.QuerySendResult;
import com.sailmi.message.core.model.dto.SendMessageResult;
import com.sailmi.message.core.model.dto.ValidateCodeDTO;
import com.sailmi.message.core.model.vo.MessageVO;

public interface IMessageService  extends BaseService<Message> {

	/**
	 * 发送短信
	 * @param message
	 * @return
	 */
	SendMessageResult send(MessageDTO message);

	/**
	 * 校验验证码
	 * @param validateCode
	 * @return
	 */
	boolean check(ValidateCodeDTO validateCode);

	/**
	 * 查询最近一条验证码短信
	 * @param mobile 手机号
	 * @param template 需要id,validateExpire字段
	 * @return
	 */
	Message queryLatestMessage(String mobile, Template template);

	/**
	 * 将短信验证状态修改为已验证
	 * @param message
	 * @return
	 */
	int updateMessageValidateStatus(Message message);

	/**
	 * 获取验证码
	 * @param length 验证码长度
	 * @return
	 */
	String getValidateCode(byte length);

	/**
	 * 查询并更新发送状态
	 * @param message
	 * @param channelSMSService
	 * @return
	 */
	QuerySendResult queryAndUpdateSendStatus(Message message, IChannelService channelSMSService);

	/**
	 * 查询发送中的短信
	 * @param fromDate 查询该时间点之后的数据
	 * @param channel 短信渠道
	 * @return
	 */
	List<Message> querySendingMessages(Date fromDate, String channel);

	/**
	 * 根据手机号和第三方id查询短信
	 * @param mobile
	 * @param bizId
	 * @return
	 */
	Message queryMessage(String mobile, String bizId);

	/**
	 * 更新发送状态
	 * @param message 更新前短信
	 * @param querySendResult 短信查询结果
	 * @return
	 */
	int updateMessageSendStatus(Message message, QuerySendResult querySendResult);

	/**
	 * 批量发送短信
	 * @param batchMessage
	 * @return
	 */
	void batchSend(BatchMessageDTO batchMessage);

	/**
	 * 查询批量短信
	 * @param bizId
	 * @return
	 */
	BatchMessage queryBatchMessage(String bizId);

	/**
	 * 更新批量发送短信结果
	 * @param id
	 * @param sending
	 * @param success
	 * @param failure
	 * @return
	 */
	int updateBatchMessageCount(int id, short sending, short success, short failure);

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param message
	 * @return
	 */
	IPage<MessageVO> selectMessagePage(IPage<MessageVO> page, MessageVO message);
}
