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

import com.sailmi.core.oss.model.GeneralFile;
import com.sailmi.core.oss.model.OssFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IOssOperator {
	public void makeBucket(String bucketName);


	public void removeBucket(String bucketName);

	public boolean bucketExists(String bucketName);

	public void copyFile(String bucketName, String fileName, String destBucketName);

	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName);

	public OssFile statFile(String fileName);

	public String filePath(String fileName);

	public String fileLink(String fileName);

	public GeneralFile putFile(MultipartFile file);

	public GeneralFile putFile(String fileName, MultipartFile file);

	public GeneralFile putFile(String bucketName, String fileName, MultipartFile file);

	public GeneralFile putFile(String fileName, InputStream stream);

	public GeneralFile putFile(String bucketName, String fileName, InputStream stream);

	public void removeFile(String fileName);

	public void removeFiles(List<String> fileNames);

	public void removeFiles(String bucketName, List<String> fileNames) ;
}
