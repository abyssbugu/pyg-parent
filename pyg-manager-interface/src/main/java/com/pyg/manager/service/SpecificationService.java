package com.pyg.manager.service;

import java.util.List;
import java.util.Map;

import com.pyg.pojo.TbSpecification;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;

public interface SpecificationService {
	
	/**
	 * 需求：查询分页类别
	 * 参数：
	 * Integer page,Interger rows
	 * 返回值：
	 * PageResult
	 */
	 PageResult findPage(Integer page, Integer rows);
	/**
	 * 需求：保存规格，保存规格选项
	 * 参数：Specification 
	 * 返回值：PygResult
	 */
	 PygResult add(Specification specification);
	/**
	 * 需求：根据id查询规格对象
	 * 参数：Long id
	 * 返回值：Specification
	 */
	 Specification findOne(Long id);
	
	/**
	 * 需求：修改规格及规格选项
	 * 参数：Specification
	 * 返回值：PygResult
	 */
	 PygResult update(Specification specification);
	/**
	 * 需求：删除规格及规格选项
	 * 参数：Long[] ids
	 * 返回值：PygResult
	 */
	 PygResult delete(Long[] ids);
	
	/**
	 * 需求：查询规格表数据，进行多项选择
	 * 返回值：List<Map>
	 */
	 List<Map> findSpecList();

}
