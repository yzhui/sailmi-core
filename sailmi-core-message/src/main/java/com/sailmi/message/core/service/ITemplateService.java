/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.sailmi.message.core.service;

import com.sailmi.message.core.dao.entity.Template;
import com.sailmi.message.core.model.vo.TemplateVO;
import com.sailmi.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author sailmi
 * @since 2020-07-17
 */
public interface ITemplateService extends BaseService<Template> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param template
	 * @return
	 */
	IPage<TemplateVO> selectTemplatePage(IPage<TemplateVO> page, TemplateVO template);

}
