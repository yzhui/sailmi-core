package com.sailmi.core.oss.provider.localfileoss.support;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Data
@Component("fsProperties")
@EnableConfigurationProperties(DefaultLocalFileProperties.class)
@ConfigurationProperties(
	prefix = "ucs.oss.localfileoss",
	ignoreInvalidFields = true,
	ignoreUnknownFields = true)
public class DefaultLocalFileProperties implements LocalFileProperties, InitializingBean {

    private String storePath;
    private String fileUrl;

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Override
    public String getStorePath() {
        return storePath;
    }

    public void setStorePath(String storePath) {
        this.storePath = storePath;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(storePath);
    }

}
