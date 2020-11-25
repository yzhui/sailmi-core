package com.sailmi.core.oss.provider.qiniu;

import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.props.OssProviderEnum;
import com.sailmi.core.oss.provider.qiniu.impl.FileStorageImpl;
import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.provider.qiniu.impl.QiuniuCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author dz
 * @since 3
 */
@Configuration
@DependsOn("ossProperties")
@Conditional(QiuniuCondition.class)
public class QiniuAutoConfiguration {

	public static final String PROVIDER_NAME = "qiniuoss";

	@Bean(name = "fileStorage")
	@Conditional(QiuniuCondition.class)
    public FileStorage fileStorage() {
		return new FileStorageImpl();
    }

}
