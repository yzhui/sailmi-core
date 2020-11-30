package com.sailmi.message.core.service;

public interface IBatchQueryable extends IChannelService {

	/**
	 * 批量查询更新发送状态
	 */
	void batchQueryAndUpdateSendStatus();

}
