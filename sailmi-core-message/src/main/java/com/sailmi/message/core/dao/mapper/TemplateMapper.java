package com.sailmi.message.core.dao.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sailmi.message.core.dao.entity.Template;
import com.sailmi.message.core.model.vo.TemplateVO;


import java.util.List;

public interface TemplateMapper extends BaseMapper<Template> {
	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param template
	 * @return
	 */
	List<TemplateVO> selectTemplatePage(IPage page, TemplateVO template);
}
