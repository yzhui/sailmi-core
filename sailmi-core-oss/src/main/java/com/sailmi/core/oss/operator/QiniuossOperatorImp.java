/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.core.oss.operator;

import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import com.sailmi.core.oss.model.GeneralFile;
import com.sailmi.core.oss.model.OssFile;
import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.rule.OssRule;
import com.sailmi.core.tool.utils.CollectionUtil;
import com.sailmi.core.tool.utils.Func;
import com.sailmi.core.tool.utils.StringPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.multipart.MultipartFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import com.qiniu.common.Constants;
import com.qiniu.util.UrlSafeBase64;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * QiniuTemplate
 *
 * @author Chill
 */
@AllArgsConstructor
@ConditionalOnProperty(value = "oss.name", havingValue = "qiniu")
public class QiniuossOperatorImp  implements IOssOperator{
	private Auth auth;
	private UploadManager uploadManager;
	private BucketManager bucketManager;
	private OssProperties ossProperties;
	private OssRule ossRule;

	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!CollectionUtil.contains(bucketManager.buckets(), getBucketName(bucketName))) {
			bucketManager.createBucket(getBucketName(bucketName), Zone.zone0().getRegion());
		}
	}


	@SneakyThrows
	public void removeBucket(String bucketName) {
		bucketManager.deleteBucket(getBucketName(bucketName));
	}


	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return CollectionUtil.contains(bucketManager.buckets(), getBucketName(bucketName));
	}


	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		bucketManager.copy(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}


	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		bucketManager.copy(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}


	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}


	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		FileInfo stat = bucketManager.stat(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(stat.key);
		ossFile.setName(Func.isEmpty(stat.key) ? fileName : stat.key);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.hash);
		ossFile.setLength(stat.fsize);
		ossFile.setPutTime(new Date(stat.putTime / 10000));
		ossFile.setContentType(stat.mimeType);
		return ossFile;
	}


	@SneakyThrows
	public String filePath(String fileName) {
		return getBucketName().concat(StringPool.SLASH).concat(fileName);
	}


	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getBucketName(bucketName).concat(StringPool.SLASH).concat(fileName);
	}


	@SneakyThrows
	public String fileLink(String fileName) {
		return ossProperties.getEndpoint().concat(StringPool.SLASH).concat(fileName);
	}


	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		String fileLink=ossProperties.getEndpoint().concat(StringPool.SLASH).concat(fileName);
		fileLink = this.getDownloadUrl(fileLink,1000);
		return fileLink;
	}


	@SneakyThrows
	public GeneralFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}


	@SneakyThrows
	public GeneralFile putFile(String fileName, MultipartFile file) {
		System.out.println("1----------------OssProper:"+ossProperties);
		return putFile(ossProperties.getBucketName(), fileName, file);
	}


	@SneakyThrows
	public GeneralFile putFile(String bucketName, String fileName, MultipartFile file) {
		System.out.println("4----------------bucketName:"+bucketName);
		return putFile(bucketName, fileName, file);
	}


	@SneakyThrows
	public GeneralFile putFile(String fileName, InputStream stream) {
		System.out.println("3----------------OssProper:"+ossProperties);
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}


	@SneakyThrows
	public GeneralFile putFile(String bucketName, String fileName, InputStream stream) {
		System.out.println("2----------------bucketName:"+bucketName);
		return put(bucketName, stream, fileName, false);
	}

	@SneakyThrows
	public GeneralFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		key = getFileName(key);
		// 覆盖上传
		if (cover) {
			uploadManager.put(stream, key, getUploadToken(bucketName, key), null, null);
		} else {
			Response response = uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
			int retry = 0;
			int retryCount = 5;
			while (response.needRetry() && retry < retryCount) {
				response = uploadManager.put(stream, key, getUploadToken(bucketName), null, null);
				retry++;
			}
		}
		GeneralFile file = new GeneralFile();
		file.setName(key);
		file.setLink(fileLink(bucketName, key));
		return file;
	}


	@SneakyThrows
	public void removeFile(String fileName) {
		bucketManager.delete(getBucketName(), fileName);
	}


	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		bucketManager.delete(getBucketName(bucketName), fileName);
	}


	@SneakyThrows
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}


	@SneakyThrows
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
	}

	/**
	 * 根据规则生成文件名称规则
	 *
	 * @param originalFilename 原始文件名
	 * @return string
	 */
	private String getFileName(String originalFilename) {
		return ossRule.fileName(originalFilename);
	}

	/**
	 * 获取上传凭证，普通上传
	 */
	public String getUploadToken(String bucketName) {
		return auth.uploadToken(getBucketName(bucketName));
	}

	/**
	 * 获取上传凭证，覆盖上传
	 */
	private String getUploadToken(String bucketName, String key) {
		return auth.uploadToken(getBucketName(bucketName), key);
	}

	/**
	 *
	 * @param baseUrl
	 * @param deadline
	 * @return
	 */
	@SneakyThrows
	public String getDownloadUrl(String baseUrl, long deadline)  {
		StringBuffer downloadUrl = new StringBuffer(baseUrl);
		long ts = (System.currentTimeMillis()/1000) + deadline;
		downloadUrl.append("?e=" + String.valueOf(ts));
		try {
			Mac mac = Mac.getInstance("HmacSHA1");
			byte[] sk = ossProperties.getSecretKey().getBytes(Constants.UTF_8);
			SecretKeySpec secretKey = new SecretKeySpec(sk, "HmacSHA1");
			mac.init(secretKey);
			String encodeSign = UrlSafeBase64.encodeToString(mac.doFinal(downloadUrl.toString().getBytes()));
			String token = ossProperties.getAccessKey() + ":" + encodeSign;
			downloadUrl.append("&token=" + token);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return downloadUrl.toString();
	}

}
