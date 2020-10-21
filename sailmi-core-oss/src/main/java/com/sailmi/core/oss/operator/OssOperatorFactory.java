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
import com.aliyun.oss.OSSClient;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.sailmi.core.oss.props.OssProperties;
import com.sailmi.core.oss.rule.OssRule;
import com.sailmi.core.tool.utils.SpringUtil;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * QiniuTemplate
 *
 * @author Chill
 */
@Component
@AllArgsConstructor
@EnableConfigurationProperties
public class OssOperatorFactory {
	private OssProperties ossProperties;
	private OssRule ossRule;

	public IOssOperator getInstance(){
		if(ossProperties.getName().equals("qiniu")){
			QiniuossOperatorImp qiniuossOperatorImp=null;
			qiniuossOperatorImp = SpringUtil.getBean(QiniuossOperatorImp.class);
			System.out.println("qiniu:"+qiniuossOperatorImp);
			return qiniuossOperatorImp;
		}else{
			AliossOperatorImp aliossOperatorImp=null;
			aliossOperatorImp = SpringUtil.getBean(AliossOperatorImp.class);
			System.out.println("alioss:"+aliossOperatorImp);

			return aliossOperatorImp;
		}
	}
}
