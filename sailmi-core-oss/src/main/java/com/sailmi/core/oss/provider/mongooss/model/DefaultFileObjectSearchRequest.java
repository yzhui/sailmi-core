package com.sailmi.core.oss.provider.mongooss.model;

import com.sailmi.core.oss.common.support.FileObjectSearchParam;

public class DefaultFileObjectSearchRequest extends FileObjectSearchParam implements FileObjectSearchRequest {

    private String bizId;

    private String category;

    private String code;

    @Override
    public String getAttachedId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
