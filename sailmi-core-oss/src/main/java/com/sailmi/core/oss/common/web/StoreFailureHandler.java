package com.sailmi.core.oss.common.web;

import com.sailmi.core.oss.common.core.StoreFileException;
import com.sailmi.core.oss.common.core.StoreFileRequest;

public interface StoreFailureHandler {

    void onStoreFailure(StoreFileRequest request, StoreFileException exception);

}
