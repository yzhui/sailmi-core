package com.sailmi.core.oss.provider.alioss.impl;

import com.sailmi.core.oss.provider.alioss.AliossAutoConfiguration;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class AliossCondition implements Condition {

	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String ossType = conditionContext.getEnvironment().getProperty("ucs.oss.provider");
		System.out.println("Ali Condition Oss Provider is:"+ossType);
		if(ossType!=null)
		{
			return ossType.equals(AliossAutoConfiguration.PROVIDER_NAME);
		}else{
			return false;
		}
	}
}
