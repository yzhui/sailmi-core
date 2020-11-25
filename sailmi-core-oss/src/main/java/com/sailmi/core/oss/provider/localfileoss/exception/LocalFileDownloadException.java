package com.sailmi.core.oss.provider.localfileoss.exception;

/**
 * @author dz
 */
public class LocalFileDownloadException extends LocalFileStoreException {

    public LocalFileDownloadException() {
    }

    public LocalFileDownloadException(String message) {
        super(message);
    }

    public LocalFileDownloadException(String message, Throwable cause) {
        super(message, cause);
    }

    public LocalFileDownloadException(Throwable cause) {
        super(cause);
    }
}
