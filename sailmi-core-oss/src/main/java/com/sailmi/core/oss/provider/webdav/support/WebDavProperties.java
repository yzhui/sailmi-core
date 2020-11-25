package com.sailmi.core.oss.provider.webdav.support;

/**
 * Config for WebDav client.
 *
 * @author dz
 */
public interface WebDavProperties {

	boolean isSslEnabled();

	String getUsername();

	String getPassword();

	String getEndpoint();

}
