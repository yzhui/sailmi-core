package com.sailmi.message.core.dao.constant;

	public class SendStatus{
		/**
		 * 发送失败
		 */
		public final static byte FAILURE = 0;
		/**
		 * 发送中
		 */
		public final static byte SENDING = 1;
		/**
		 * 发送成功
		 */
		public final static byte SUCCESS = 2;
		/**
		 * 发送完成，存在部分失败（批量短信状态）
		 */
		public final static byte COMPLETE = 3;

}
