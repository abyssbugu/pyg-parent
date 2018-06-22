package com.pyg.manager.service;

import com.pyg.pojo.TbTypeTemplate;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
	
	/**
	 * 需求：查询分页类别
	 * 参数：
	 * Integer page,Interger rows
	 * 返回值：
	 * PageResult
	 */
	 PageResult findPage(Integer page, Integer rows);
	/**
	 * 需求：保存模板数据
	 * 参数：TbTypeTemplate 
	 * 返回值：PygResult
	 */
	 PygResult add(TbTypeTemplate typeTemplate);
	/**
	 * 需求：根据id查询模板对象
	 * 参数：Long id
	 * 返回值：TbTypeTemplate
	 */
	 TbTypeTemplate findOne(Long id);
	
	/**
	 * 需求：修改模板
	 * 参数：TbTypeTemplate
	 * 返回值：PygResult
	 */
	 PygResult update(TbTypeTemplate typeTemplate);
	/**
	 * 需求：删除模板
	 * 参数：Long[] ids
	 * 返回值：PygResult
	 */
	 PygResult delete(Long[] ids);

	/**
	 * 需求：根据模板id查询模板规格属性值，根据规格id查询规格选项
	 * 参数：模板id
	 * 返回值：List<Map>
	 */
	public List<Map> findSpecList(Long id);

}
