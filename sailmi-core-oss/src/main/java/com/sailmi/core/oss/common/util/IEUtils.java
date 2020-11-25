package com.sailmi.core.oss.common.util;

public class IEUtils {

    public static String toStandardContentType(String contentType) {
        if ("image/pjpeg".equals(contentType) || "image/jpg".equals(contentType)) {
            return "image/jpeg";
        }
        else if ("image/x-png".equals(contentType)) {
            return "image/png";
        }
        return contentType;
    }

}
