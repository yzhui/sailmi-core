package com.sailmi.message.core.dao.constant;

/**
 * 消息类型定义
 * 对应数据库里Template表的message_type字段
 */

public class MessageTypes {

	/**
	 * 手机短信
	 */
	public final static int SMS = 0;

	/**
	 * 邮件
	 */
	public final static int MAIL = 1;

	/**
	 * APP推送
	 */
	public final static int APNS = 2;

	/**
	 * 桌面推送
	 */
	public final static int DESKNOTIFY = 3;

	/**
	 * 站内信
	 */
	public final static int SYSNOTIFY = 4;


}
