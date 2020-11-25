package com.sailmi.core.oss.provider.mongooss.model;

public interface FileObjectSearchRequest extends com.sailmi.core.oss.common.domain.request.FileObjectSearchRequest {

    String getAttachedId();

    String getCategory();

    String getCode();

}
