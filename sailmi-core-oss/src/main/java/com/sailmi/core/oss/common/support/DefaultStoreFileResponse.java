package com.sailmi.core.oss.common.support;

import com.sailmi.core.oss.common.core.StoreFileResponse;
import com.sailmi.core.oss.common.core.StoredFileObject;

public class DefaultStoreFileResponse implements StoreFileResponse {

    private String providerName;

    private StoredFileObject fileObject;

    public DefaultStoreFileResponse(String provider, StoredFileObject fileObject) {
        this.providerName = provider;
        this.fileObject = fileObject;
    }

    @Override
    public String getProviderName() {
        return providerName;
    }

    @Override
    public StoredFileObject getStoredFileObject() {
        return fileObject;
    }

}
