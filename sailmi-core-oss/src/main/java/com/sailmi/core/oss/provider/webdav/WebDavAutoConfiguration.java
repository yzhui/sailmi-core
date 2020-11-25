package com.sailmi.core.oss.provider.webdav;

import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.props.OssProviderEnum;
import com.sailmi.core.oss.provider.OssProvider;
import com.sailmi.core.oss.provider.mongooss.impl.MongoCondition;
import com.sailmi.core.oss.provider.webdav.impl.FileStorageImpl;
import com.sailmi.core.oss.provider.webdav.impl.WebDavCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
@DependsOn("ossProperties")
@Conditional(WebDavCondition.class)
public class WebDavAutoConfiguration {
	@Autowired
	OssProperties ossProperties;

	public static final String PROVIDER_NAME = "webdavoss";

    @Bean(name = "webdavossStorage")
    public FileStorage fileStorage() {
		if(ossProperties.getProvider().equals(OssProviderEnum.webdavoss)) {
			return new FileStorageImpl();
		}else{
			return null;
		}
    }

}
