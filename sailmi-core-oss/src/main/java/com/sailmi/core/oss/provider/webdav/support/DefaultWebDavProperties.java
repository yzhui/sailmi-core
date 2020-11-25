package com.sailmi.core.oss.provider.webdav.support;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author dz
 */

@Data
@EnableConfigurationProperties(DefaultWebDavProperties.class)
@ConfigurationProperties(
	prefix = "sailmi.fss.webdavoss",
	ignoreInvalidFields = true,
	ignoreUnknownFields = true)
public class DefaultWebDavProperties implements WebDavProperties, InitializingBean {

    private boolean sslEnabled = false;

    private String username;

    private String password;

    private String endpoint;

    @Override
    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(username);
        Assert.notNull(password);
        Assert.notNull(endpoint);
    }

}
