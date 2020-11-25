package com.sailmi.core.oss.provider.alioss;

import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.props.OssProviderEnum;
import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.provider.alioss.impl.AliossCondition;
import com.sailmi.core.oss.provider.alioss.impl.FileStorageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("ossProperties")
@Conditional(AliossCondition.class)
public class AliossAutoConfiguration {

	public static final String PROVIDER_NAME = "alioss";

	@Bean(name = "fileStorage")
	@Conditional(AliossCondition.class)
	public FileStorage fileStorage() {
		return new FileStorageImpl();
	}

}
