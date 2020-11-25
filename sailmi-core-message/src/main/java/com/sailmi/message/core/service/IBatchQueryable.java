package com.sailmi.message.core.service;

public interface IBatchQueryable extends com.sailmi.message.core.service.IChannelSMSService {

	/**
	 * 批量查询更新发送状态
	 */
	void batchQueryAndUpdateSendStatus();

}
