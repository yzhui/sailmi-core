package com.sailmi.core.oss.provider.webdav.impl;

import com.sailmi.core.oss.common.core.StoreFileRequest;
import com.sailmi.core.oss.common.core.StoredFileObject;
import com.sailmi.core.oss.common.domain.model.FileObject;
import com.sailmi.core.oss.common.support.DefaultFileObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dz
 * @since 3
 */
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

    private WebDavFile webDavFile;

    @Override
    public String getProviderName() {
        return this.providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    @Override
    public WebDavFile getImplementation() {
        return this.webDavFile;
    }

    public void setImplementation(WebDavFile webDavFile) {
        this.webDavFile = webDavFile;
    }

    @Override
    public void writeTo(OutputStream outputStream, int bufferSize) throws IOException {
        if (getImplementation() == null) {
            throw new UnsupportedOperationException("The stored file implementation is not supplied.");
        }
        if (bufferSize <= 0) {
            bufferSize = 1024 * 4;
        }

        webDavFile.writeTo(outputStream, bufferSize);
    }

    @Override
    public String toString() {
        return "DefaultStoredFileObject{" + "providerName='" + providerName + '\'' + ", webDavFile=" + webDavFile +
                "} " + super.toString();
    }
}
