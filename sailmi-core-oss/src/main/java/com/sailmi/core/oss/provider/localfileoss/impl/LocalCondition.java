package com.sailmi.core.oss.provider.localfileoss.impl;

import com.sailmi.core.oss.provider.localfileoss.LocalFileAutoConfiguration;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LocalCondition implements Condition {


	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String ossType = conditionContext.getEnvironment().getProperty("ucs.oss.provider");
		System.out.println("Localfile Condition Oss Provider is:"+ossType);
		if(ossType!=null)
		{
			return ossType.equals(LocalFileAutoConfiguration.PROVIDER_NAME);
		}else{
			return false;
		}
	}
}
