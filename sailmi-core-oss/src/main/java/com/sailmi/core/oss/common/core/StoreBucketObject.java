package com.sailmi.core.oss.common.core;

/**
 *  文档库对象
 */
public interface StoreBucketObject {
	String getBucketName();
	String getBucketSecret();
	String getBucketKey();

	/**
	 * 创建一个bucket
	 * @param bucketName
	 * @return
	 */
	boolean createBucket(String bucketName);
}
