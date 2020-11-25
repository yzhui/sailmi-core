package com.sailmi.core.oss.common.web;

import com.sailmi.core.oss.common.core.StoreFileRequest;
import com.sailmi.core.oss.common.core.StoreFileResponse;

public interface StoreSuccessHandler<T> {

    T onStoreSuccess(StoreFileRequest request, StoreFileResponse response);

}
