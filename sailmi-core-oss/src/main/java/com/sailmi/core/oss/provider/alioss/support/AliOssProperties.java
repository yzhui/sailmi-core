package com.sailmi.core.oss.provider.alioss.support;

import com.aliyun.oss.ClientConfiguration;
import com.sailmi.core.oss.provider.qiniu.support.DefaultQiniuProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.util.Map;

/**
 * Config for oss client.
 *
 * @author dz
 */


public interface AliOssProperties {

    /**
     * The oss access key id
     */
    String getKeyId();


    /**
     * The oss access secret
     */
    String getKeySecret();

    /**
     * The oss domain base
     */
    String getEndpoint();

    /**
     * The default bucket to store the file which's bucket is not specified for category.
     */
    String getDefaultBucket();

    /**
     * The file's category and bucket mapping, the file will be stored in corresponding bucket for different category,
     * if the bucket is not defined for the file's category , the default bucket will be taken place.
     */
    Map<String, String> getBuckets();

    /**
     *
     * @return
     */
    ClientConfiguration getClientConfiguration();


    String getProxyHost();

    int getProxyPort();

    void setProxyHost(String proxyHost);

    void setProxyPort(int proxyPort);

}
