package com.sailmi.core.oss.provider.localfileoss.exception;

import com.sailmi.core.oss.common.core.StoreFileException;

/**
 * @author dz
 */
public class LocalFileStoreException extends StoreFileException {

	public LocalFileStoreException() {
	}

	public LocalFileStoreException(String message) {
		super(message);
	}

	public LocalFileStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public LocalFileStoreException(Throwable cause) {
		super(cause);
	}
}
