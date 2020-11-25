package com.sailmi.core.oss.provider.qiniu.exception;

import com.sailmi.core.oss.common.core.StoreFileException;

/**
 * @author dz
 * @since 3
 */
public class QiniuStoreException extends StoreFileException {
    public QiniuStoreException() {
    }

    public QiniuStoreException(String message) {
        super(message);
    }

    public QiniuStoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public QiniuStoreException(Throwable cause) {
        super(cause);
    }
}
