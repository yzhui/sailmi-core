package com.sailmi.core.oss.provider.qiniu.impl;

import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.provider.mongooss.MongoAutoConfiguration;
import com.sailmi.core.oss.provider.qiniu.QiniuAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class QiuniuCondition implements Condition {

	@Override
	public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
		String ossType = conditionContext.getEnvironment().getProperty("ucs.oss.provider");
		System.out.println("Qiniu Condition Oss Provider is:"+ossType);
		if(ossType!=null)
		{
			return ossType.equals(QiniuAutoConfiguration.PROVIDER_NAME);
		}else{
			return false;
		}
	}
}
