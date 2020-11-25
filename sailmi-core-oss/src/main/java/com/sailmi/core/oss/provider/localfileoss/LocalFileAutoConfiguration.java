package com.sailmi.core.oss.provider.localfileoss;

import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.provider.localfileoss.impl.FileStorageImpl;
import com.sailmi.core.oss.provider.localfileoss.impl.LocalCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("ossProperties")
@Conditional(LocalCondition.class)
public class LocalFileAutoConfiguration {

	public static final String PROVIDER_NAME = "localfileoss";

	@Bean(name = "fileStorage")
	@Conditional(LocalCondition.class)
    public FileStorage localFileStorage() {
		return new FileStorageImpl();
    }

}
