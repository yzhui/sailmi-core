package com.sailmi.core.oss.provider.alioss.impl;

import com.aliyun.oss.model.OSSObject;
import com.sailmi.core.oss.common.core.StoreFileRequest;
import com.sailmi.core.oss.common.core.StoredFileObject;
import com.sailmi.core.oss.common.domain.model.FileObject;
import com.sailmi.core.oss.common.support.DefaultFileObject;
import com.sailmi.core.oss.common.util.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultStoredFileObject extends DefaultFileObject implements StoredFileObject {

    public static DefaultStoredFileObject from(StoreFileRequest request) {
        if (request == null) {
            return null;
        }
        DefaultStoredFileObject result = new DefaultStoredFileObject();
        BeanUtils.copyProperties(request, result);
        return result;
    }

    public static DefaultStoredFileObject from(FileObject fileObject) {
        if (fileObject == null) {
            return null;
        }
        DefaultStoredFileObject result = new DefaultStoredFileObject();
        BeanUtils.copyProperties(fileObject, result);
        return result;
    }

    private String providerName;

    private OssObjectProxy ossObject;

    @Override
    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public OssObjectProxy getImplementation() {
        return ossObject;
    }

    public void setImplementation(OssObjectProxy ossObject) {
        this.ossObject = ossObject;
    }

    @Override
    public void writeTo(OutputStream outputStream, int bufferSize) throws IOException {
        if (getImplementation() == null) {
            throw new UnsupportedOperationException("The stored file implementation is not supplied.");
        }
        if (bufferSize <= 0) {
            bufferSize = 1024 * 4;
        }

        ossObject.writeTo(outputStream, bufferSize);
    }

    @Override
    public String toString() {
        return "DefaultStoredFileObject{" + "providerName='" + providerName + '\'' + ", ossObject=" + ossObject + "} " +
                super.toString();
    }
}
