package com.sailmi.message.core.model.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sailmi.message.core.model.validation.Mobile;
import com.sailmi.message.core.model.validation.TemplateId;

public class MessageDTO {

	/**
	 * 手机号码
	 */
	@Mobile
	private String mobile;

	//一个消息，有可能会通过多个渠道发送。在这个list中，存储与发送类型相关的信息
	//在发送时，会遍历此清单
	private List messageType;


	/**
	 * 模板id
	 */
	@TemplateId
	private String templateId;

	/**
	 * 短信参数，不超过200个字符
	 */
	private Map<String, Object> params;


	public MessageDTO() {
		messageType = new ArrayList();
	}

	public List getMessageType() {
		return messageType;
	}

	public void setMessageType(List messageType) {
		this.messageType = messageType;
	}

	public void addMessageType(int messageType){
		this.messageType.add(messageType);
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "MessageDTO [mobile=" + mobile + ", templateId=" + templateId + ", params=" + params + "]";
	}

}
