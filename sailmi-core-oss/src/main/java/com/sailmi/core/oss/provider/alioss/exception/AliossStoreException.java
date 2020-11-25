package com.sailmi.core.oss.provider.alioss.exception;

import com.sailmi.core.oss.common.core.StoreFileException;

/**
 * @author dz
 */
public class AliossStoreException extends StoreFileException {
	public AliossStoreException() {
	}

	public AliossStoreException(String message) {
		super(message);
	}

	public AliossStoreException(String message, Throwable cause) {
		super(message, cause);
	}

	public AliossStoreException(Throwable cause) {
		super(cause);
	}
}
