package com.sailmi.core.oss.provider.webdav.impl;

import com.sailmi.core.oss.provider.qiniu.QiniuAutoConfiguration;
import com.sailmi.core.oss.provider.webdav.WebDavAutoConfiguration;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WebDavCondition implements Condition {

	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String ossType = conditionContext.getEnvironment().getProperty("ucs.oss.provider");
		System.out.println("WebDav Condition Oss Provider is:"+ossType);
		if(ossType!=null)
		{
			return ossType.equals(WebDavAutoConfiguration.PROVIDER_NAME);
		}else{
			return false;
		}
	}
}
