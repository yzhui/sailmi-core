package com.sailmi.core.oss.provider.alioss.support;

import com.aliyun.oss.ClientConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dz
 */
@Component("aliossProperties")
@EnableConfigurationProperties(DefaultAliOssProperties.class)
@ConfigurationProperties(
	prefix = "ucs.oss.alioss",
	ignoreInvalidFields = true,
	ignoreUnknownFields = true)
public class DefaultAliOssProperties implements AliOssProperties, InitializingBean {

    private String keyId;

    private String keySecret;

    private String endpoint;

    private String defaultBucket;

    private String proxyHost;

    private int proxyPort;

    private Map<String, String> buckets = new HashMap<String, String>();

    private ClientConfiguration clientConfiguration;

    @Override
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    @Override
	public String getProxyHost(){
    	return this.proxyHost;
	}

	@Override
	public int getProxyPort(){
    	return this.proxyPort;
	}

	@Override
	public void setProxyHost(String proxyHost){
    	this.proxyHost = proxyHost;
	}

	@Override
	public void setProxyPort(int proxyPort){
    	this.proxyPort = proxyPort;
	}

    @Override
    public String getKeySecret() {
        return keySecret;
    }

    public void setKeySecret(String keySecret) {
        this.keySecret = keySecret;
    }

    @Override
    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public String getDefaultBucket() {
        return defaultBucket;
    }

    public void setDefaultBucket(String defaultBucket) {
        this.defaultBucket = defaultBucket;
    }

    @Override
    public Map<String, String> getBuckets() {
        return buckets;
    }

    public void setBuckets(Map<String, String> buckets) {
        this.buckets = buckets;
    }

    @Override
    public ClientConfiguration getClientConfiguration() {
        return clientConfiguration;
    }

    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(keyId);
        Assert.notNull(keySecret);
        Assert.notNull(endpoint);
        Assert.notNull(defaultBucket);
    }

}
