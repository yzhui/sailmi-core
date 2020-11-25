package com.sailmi.core.oss.provider.alioss.impl;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.sailmi.core.oss.provider.alioss.exception.AliossStoreException;
import com.sailmi.core.oss.provider.alioss.support.AliOssProperties;
import com.sailmi.core.oss.common.core.*;
import com.sailmi.core.oss.common.support.DefaultStoreFileResponse;
import com.sailmi.core.oss.common.util.IOUtils;
import com.sailmi.core.oss.common.util.MetadataUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dz
 */
public class FileStorageImpl implements FileStorage, InitializingBean, DisposableBean {

    private static final Log logger = LogFactory.getLog(FileStorageImpl.class);

    public static final String PROVIDER_NAME = "alioss";

    @Autowired
    private AliOssProperties aliossProperties;

    private OSSClient ossClient;

    @Override
    public String getName() {
        return PROVIDER_NAME;
    }

    @Override
    public boolean isMetadataSupported() {
        return true;
    }

    @Override
    public StoreFileResponse store(InputStream inputStream, StoreFileRequest request) throws StoreFileException {
        String ossBucket = resolveBucket(request);
        String ossObjectName = MetadataUtils.generateFilename(request, true);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(request.getContentType());
        objectMetadata.setUserMetadata(MetadataUtils.buildMetadata(request));

        PutObjectResult putObjectResult = ossClient.putObject(ossBucket, ossObjectName, inputStream, objectMetadata);

        if (putObjectResult == null) {
            throw new StoreFileException(String.format("Fail to upload %s", request.getOriginalFilename()));
        }

        if (putObjectResult.getResponse() != null) {
            if (putObjectResult.getResponse().getStatusCode() != 200) {
                throw new StoreFileException(String.format("Fail to upload %s", request.getOriginalFilename()));
            }

            try {
                logger.debug(String.format("Response for uploading %s : %s",
                                           request.getOriginalFilename(),
                                           IOUtils.readAsString(putObjectResult.getResponse().getContent())));
            } catch (Throwable e) {
                logger.error("Fail to parse uploading response content", e);
                IOUtils.close(putObjectResult.getResponse().getContent());
            }
        }

        logger.debug(String.format("The uploading %s stored as oss-object[bucket=%s,object=%s]",
                                   request.getOriginalFilename(),
                                   ossBucket,
                                   ossObjectName));

        DefaultStoredFileObject fileObject = DefaultStoredFileObject.from(request);

        String url = new StringBuilder("https://").append(ossBucket)
                                                  .append(".")
                                                  .append(this.aliossProperties.getEndpoint())
                                                  .append("/")
                                                  .append(ossObjectName)
                                                  .toString();

        fileObject.getAttributes().put("oss-bucket", ossBucket);
        fileObject.getAttributes().put("oss-object", ossObjectName);
        fileObject.getAttributes().put("oss-url", url);

        String uploadedAt = objectMetadata.getUserMetadata().get("oss-uploadedAt");
        fileObject.setUploadedAt(uploadedAt != null ? new Date(Long.parseLong(uploadedAt)) : null);

        fileObject.setFileUrl(url);
        fileObject.setStoredFilename(ossBucket + ":" + ossObjectName);
        fileObject.setProviderName(PROVIDER_NAME);
        fileObject.setImplementation(new OssObjectProxy(ossClient, ossBucket, ossObjectName));

        return new DefaultStoreFileResponse(PROVIDER_NAME, fileObject);
    }

	@Override
	public InputStream getFile(String fileName){
		return null;
	}

    @Override
    public StoreFileResponse store(File file, StoreFileRequest request) throws StoreFileException {
        try {
            return store(new FileInputStream(file), request);
        } catch (FileNotFoundException e) {
            throw new AliossStoreException(file.getName() + " not found.", e);
        }
    }

    @Override
    public StoreFileResponse store(byte[] bytes, StoreFileRequest request) throws StoreFileException {
        return store(new ByteArrayInputStream(bytes), request);
    }

    @Override
    public StoredFileObject findByStoredFilename(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return null;
        }

        if (filename.indexOf("?") > 0) {
            filename = filename.substring(0, filename.indexOf("?"));
        }

        if (filename.indexOf(":") <= 0) {
            throw new AliossStoreException(String.format(
                    "Invalid filename %s , the format should be bucket_name:object_name",
                    filename));
        }

        String ossBucket = filename.split(":")[0];
        String ossObjectKey = filename.split(":")[1];

        OSSObject ossObject = ossClient.getObject(ossBucket, ossObjectKey);
        if (ossObject == null) {
            return null;
        }

        DefaultStoredFileObject fileObject = new DefaultStoredFileObject();

        buildStoreFileObject(ossObject, fileObject);

        String fileUrl = ossObject.getObjectMetadata().getUserMetadata().get("oss-url");

        fileObject.setFileUrl(fileUrl);
        fileObject.setStoredFilename(filename);
        fileObject.setProviderName(PROVIDER_NAME);
        fileObject.setImplementation(new OssObjectProxy(ossObject));

        return fileObject;
    }

    @Override
    public StoredFileObject findByStoredFilename(String filename, String downloadUrl) {
        logger.warn(String.format("Caution: The download url[%s] will be skipped", downloadUrl));
        return findByStoredFilename(filename);
    }

    @Override
    public StoredFileObject delete(String filename) {
        if (StringUtils.isEmpty(filename)) {
            return null;
        }

        if (filename.indexOf("?") > 0) {
            filename = filename.substring(0, filename.indexOf("?"));
        }

        if (filename.indexOf(":") <= 0) {
            throw new AliossStoreException(String.format(
                    "Invalid filename %s , the format should be bucket_name:object_name",
                    filename));
        }

        String ossBucket = filename.split(":")[0];
        String ossObjectKey = filename.split(":")[1];

        OSSObject ossObject = ossClient.getObject(ossBucket, ossObjectKey);
        if (ossObject == null) {
            return null;
        }

        DefaultStoredFileObject fileObject = new DefaultStoredFileObject();

        buildStoreFileObject(ossObject, fileObject);
        fileObject.setStoredFilename(filename);
        fileObject.setProviderName(PROVIDER_NAME);
        fileObject.setImplementation(null);

        try {
            ossClient.deleteObject(ossBucket, ossObjectKey);
            logger.debug(String.format("The oss-object[bucket=%s,object=%s] is deleted.", ossBucket, ossObjectKey));
        } catch (Throwable e) {
            logger.error(String.format("Fail to delete the oss-object[bucket=%s,object=%s]", ossBucket, ossObjectKey),
                         e);
        }

        return fileObject;
    }

    private void buildStoreFileObject(OSSObject ossObject, DefaultStoredFileObject fileObject) {
        Map<String, String> userMetadata = ossObject.getObjectMetadata().getUserMetadata();

        if (userMetadata == null) {
            return;
        }

        try {
            fileObject.setOriginalFilename(userMetadata.get("oss-originalfilename"));
            fileObject.setPrettyFilename(userMetadata.get("oss-prettyfilename"));
            fileObject.setContentType(userMetadata.get("oss-contenttype"));
            fileObject.setUploadedBy(userMetadata.get("oss-uploadedby"));

            String uploadedAt = userMetadata.get("oss-uploadedat");
            fileObject.setUploadedAt(uploadedAt != null ? new Date(Long.parseLong(uploadedAt)) : null);

            String size = userMetadata.get("oss-size");
            fileObject.setSize(size != null ? Long.parseLong(size) : -1);
        } catch (Throwable e) {
            logger.error(e, e);
        }

        try {
            Map<String, String> attributes = new HashMap<>();
            userMetadata.keySet().stream().filter(key -> key.startsWith("oss-attrs-")).forEach(key -> {
                String attributeName = key.substring("oss-attrs-".length());
                attributes.put(attributeName, userMetadata.get(key));
            });
            fileObject.setAttributes(attributes);
        } catch (Throwable e) {
            logger.error(e, e);
        }
    }

    private String resolveBucket(StoreFileRequest request) {
        String category = request.getAttributes().get("category");
        String bucket = aliossProperties.getBuckets().get(category);
        if (StringUtils.isEmpty(bucket)) {
            bucket = aliossProperties.getDefaultBucket();
        }
        return bucket;
    }

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.aliossProperties);
        this.ossClient = new OSSClient("http://" + this.aliossProperties.getEndpoint(),
                                       this.aliossProperties.getKeyId(),
                                       this.aliossProperties.getKeySecret());

        if (this.aliossProperties.getClientConfiguration() != null) {
        	System.out.println("Client Configration Proxy Host is:"+this.aliossProperties.getClientConfiguration().getProxyHost());
			System.out.println("Client Configration Proxy Port is:"+this.aliossProperties.getClientConfiguration().getProxyPort());
            if(this.aliossProperties.getClientConfiguration().getProxyPort()>0){
				BeanUtils.copyProperties(this.aliossProperties.getClientConfiguration(),
					this.ossClient.getClientConfiguration());
			}else{
				BeanUtils.copyProperties(this.aliossProperties.getClientConfiguration(),
					this.ossClient.getClientConfiguration(),"proxyPort");
			}
        }
    }

    @Override
    public void destroy() {
        if (this.ossClient != null) {
            this.ossClient.shutdown();
        }
    }
}
