package com.sailmi.core.oss.provider.qiniu.support;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * @author dz
 * @since 3
 */

@Data
@EnableConfigurationProperties(DefaultQiniuProperties.class)
@ConfigurationProperties(
	prefix = "ucs.oss.qiniuoss",
	ignoreInvalidFields = true,
	ignoreUnknownFields = true)
public class DefaultQiniuProperties implements QiniuProperties, InitializingBean {

    private String host;

    private String accessKey;

    private String secretKey;

    private String defaultBucket;

    private Map<String, String> buckets = new HashMap<String, String>();

    @Override
    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
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
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(host);
        Assert.notNull(accessKey);
        Assert.notNull(secretKey);
        Assert.notNull(defaultBucket);
    }

}
