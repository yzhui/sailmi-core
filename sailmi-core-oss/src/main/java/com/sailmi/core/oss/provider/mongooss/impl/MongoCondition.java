package com.sailmi.core.oss.provider.mongooss.impl;

import com.sailmi.core.oss.provider.mongooss.MongoAutoConfiguration;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class MongoCondition implements Condition {

	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String ossType = conditionContext.getEnvironment().getProperty("ucs.oss.provider");
		System.out.println("Mongo Condition Oss Provider is:"+ossType);
		if(ossType!=null)
		{
			return ossType.equals(MongoAutoConfiguration.PROVIDER_NAME);
		}else{
			return false;
		}
	}
}
