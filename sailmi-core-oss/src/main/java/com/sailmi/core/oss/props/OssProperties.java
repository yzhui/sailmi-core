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
package com.sailmi.core.oss.props;

import com.sailmi.core.oss.common.core.FileStorage;
import com.sailmi.core.oss.provider.OssProvider;
import com.sailmi.core.oss.provider.alioss.impl.FileStorageImpl;
import lombok.Data;
import com.sailmi.core.tool.support.Kv;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Minio参数配置类
 *
 * @author Chill
 */
@Data
@Component("ossProperties")
@EnableConfigurationProperties(OssProperties.class)
@ConfigurationProperties(prefix = "sailmi.oss")
public class OssProperties {
	/**
	 * 是否启用
	 */
	private Boolean enabled;

	/**
	 * 对象存储名称
	 */
	private String provider;

	/**
	 * 对象存储桶
	 */
	private String buckets;

	/**
	 * 文件的访问地址，对于本地存储且没有文件服务器的用户使用
	 */
	private String fileUrl;

	/**
	 * 是否开启租户模式
	 */
	private Boolean tenantMode;

	/**
	 * 自定义属性
	 */
	private Kv args;
}
