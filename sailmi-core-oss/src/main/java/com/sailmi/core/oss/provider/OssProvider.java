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
package com.sailmi.core.oss.provider;

import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.common.core.StoreFileResponse;
import com.sailmi.core.oss.common.core.StoredFileObject;
import com.sailmi.core.oss.common.support.DefaultStoreFileRequest;
import com.sailmi.core.oss.model.GeneralFile;
import com.sailmi.core.oss.model.OssFile;
import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.tool.context.ApplicationContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;

@DependsOn("ossProperties")
@Component("ossProvider")
public class OssProvider {
	@Autowired
	private OssProperties ossProperties;
	@Autowired
	private FileStorage fileStorage;

	DefaultStoreFileRequest request = null;

	public OssProvider(){
		request = new DefaultStoreFileRequest();
	}

	/**
	 * 为前端获取文件输出流
	 * @param fileName
	 * @return
	 */
	public InputStream getFile(String buckets,String fileName){

		return fileStorage.getFile(fileName);
	}

	/**
	 * 获取文件信息
	 * @param fileName
	 * @return
	 */
	public OssFile statFile(String buckets,String fileName){
		StoredFileObject sfo = fileStorage.findByStoredFilename(fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(sfo.getStoredFilename());
		ossFile.setLink(sfo.getFileUrl());
		return ossFile;
	}

	/**
	 * 获取文件存储路径
	 * @param fileName
	 * @return
	 */
	public String filePath(String buckets,String fileName){
		StoredFileObject sfo = fileStorage.findByStoredFilename(fileName);
		return sfo.getFileUrl();
	}

	/**
	 * 获取文件访问路径
	 * @param fileName
	 * @return
	 */
	public String fileLink(String buckets,String fileName){
		StoredFileObject sfo = fileStorage.findByStoredFilename(fileName);
		return sfo.getFileUrl();
	}

	/**
	 * 上传文件到服务器
	 * @param file
	 * @return
	 */
	public GeneralFile putFile(String buckets,MultipartFile file){
		return putFile(file.getName(),file);
	}

	/**
	 * 上传文件到服务器并重命名
	 * @param fileName
	 * @param file
	 * @return
	 */
	public GeneralFile putFile(String buckets,String fileName, MultipartFile file){
		DefaultStoreFileRequest request = new DefaultStoreFileRequest();
		request.setOriginalFilename(file.getOriginalFilename());
		request.setPrettyFilename(fileName);
		request.setContentType(file.getContentType());
		request.setBuckets(buckets);
		if(fileStorage == null) System.out.println("file storage is null!");
		if(file == null) System.out.println("file is null!");
		try{
			StoreFileResponse response = fileStorage.store(file.getInputStream(), request);
			StoredFileObject storedFileObject = response.getStoredFileObject();
			GeneralFile generalFile = new GeneralFile();
			generalFile.setOriginalName(storedFileObject.getOriginalFilename());
			generalFile.setName(storedFileObject.getStoredFilename());
			generalFile.setLink(storedFileObject.getFileUrl());
			return generalFile;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 移徐服务器文件
	 * @param fileName
	 */
	public void removeFile(String buckets,String fileName){
		fileStorage.delete(fileName);
	}

	/**
	 * 批量移除服务器端文件
	 * @param fileNames
	 */
	public void removeFiles(String buckets,List<String> fileNames){
		for(int i=0;i<fileNames.size();i++){
			removeFile(buckets,fileNames.get(i));
		}
	}

}
