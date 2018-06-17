package com.pyg.manager.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.SpecificationService;
import com.pyg.pojo.TbSpecification;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;

import sun.print.resources.serviceui;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
	
	//注入服务层对象
	@Reference(timeout=100000)
	private SpecificationService specificationService;
	
	
	/**
	 * 需求：查询分页类别
	 * 参数：
	 * Integer page,Interger rows
	 * 返回值：
	 * PageResult
	 */
	@RequestMapping("findPage")
	public PageResult findPage(@RequestParam(defaultValue="1") Integer page,
			@RequestParam(defaultValue="10") Integer rows) {
		//调用远程服务方法
		PageResult result = specificationService.findPage(page, rows);
		return result;
	}
	
	/**
	 * 需求：保存规格，保存规格选项
	 * 参数：Specification 
	 * 返回值：PygResult
	 */
	@RequestMapping("add")
	public PygResult add(@RequestBody Specification specification) {
		//调用远程服务方法
		PygResult result = specificationService.add(specification);
		return result;
	}
	
	/**
	 * 需求：根据id查询规格对象 
	 * 参数：Long id 
	 * 返回值：TbSpecification
	 */
	@RequestMapping("findOne")
	public Specification findOne(Long id) {
		Specification specification = specificationService.findOne(id);
		return specification;
	}
	
	/**
	 * 需求：修改规格及规格选项 
	 * 参数：Specification 
	 * 返回值：PygResult
	 */
	@RequestMapping("update")
	public PygResult update(@RequestBody Specification specification) {
		PygResult result = specificationService.update(specification);
		return result;
	}
	
	/**
	 * 需求：删除规格及规格选项 
	 * 参数：Long[] ids 
	 * 返回值：PygResult
	 */
	@RequestMapping("delete")
	public PygResult delete(Long[] ids) {
		//远程调用服务层方法
		PygResult result = specificationService.delete(ids);
		return result;
	}
	
	/**
	 * 需求：查询规格表数据，进行多项选择
	 * 返回值：List<Map>
	 */
	@RequestMapping("findSpecList")
	public List<Map> findSpecList(){
		//调用远程service服务方法
		List<Map> list = specificationService.findSpecList();
		return list;
	}
	

}
