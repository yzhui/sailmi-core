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

package com.sailmi.core.log.config;

import lombok.AllArgsConstructor;
import com.sailmi.core.log.aspect.ApiLogAspect;
import com.sailmi.core.log.event.ApiLogListener;
import com.sailmi.core.log.event.UsualLogListener;
import com.sailmi.core.log.event.ErrorLogListener;
import com.sailmi.core.log.logger.AppLogger;
import com.sailmi.core.launch.props.LaunchProperties;
import com.sailmi.core.launch.server.ServerInfo;
import com.sailmi.core.log.feign.ILogClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class AppLogToolAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final LaunchProperties launchProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public AppLogger appLogger() {
		return new AppLogger();
	}

	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, launchProperties);
	}

	@Bean
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, launchProperties);
	}

	@Bean
	public UsualLogListener logEventListener() {
		return new UsualLogListener(logService, serverInfo, launchProperties);
	}

}
