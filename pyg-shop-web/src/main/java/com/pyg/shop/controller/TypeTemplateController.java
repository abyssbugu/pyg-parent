package com.pyg.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.TypeTemplateService;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
	
	//注入服务层对象
	@Reference(timeout=100000)
	private TypeTemplateService typeTemplateService;
	
	
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
		PageResult result = typeTemplateService.findPage(page, rows);
		return result;
	}
	
	/**
	 * 需求：保存模板数据
	 * 参数：TbTypeTemplate 
	 * 返回值：PygResult
	 */
	@RequestMapping("add")
	public PygResult add(@RequestBody TbTypeTemplate tbTypeTemplate) {
		//调用服务层方法
		PygResult result = typeTemplateService.add(tbTypeTemplate);
		return result;
	}
	
	/**
	 * 需求：根据id查询模板对象
	 * 参数：Long id
	 * 返回值：TbTypeTemplate
	 */
	@RequestMapping("findOne")
	public TbTypeTemplate findOne(Long id) {
		TbTypeTemplate typeTemplate = typeTemplateService.findOne(id);
		return typeTemplate;
	}
	
	/**
	 * 需求：修改模板
	 * 参数：TbTypeTemplate
	 * 返回值：PygResult
	 */
	@RequestMapping("update")
	public PygResult update(@RequestBody TbTypeTemplate typeTemplate) {
		PygResult result = typeTemplateService.update(typeTemplate);
		return result;
	}
	
	
	/**
	 * 需求：删除模板
	 * 参数：Long[] ids
	 * 返回值：PygResult
	 */
	@RequestMapping("delete")
	public PygResult delete(Long[] ids) {
		PygResult result = typeTemplateService.delete(ids);
		return result;
	}

	/**
	 * 需求：根据模板id查询模板规格属性值，根据规格id查询规格选项
	 * 参数：模板id
	 * 返回值：List<Map>
	 */
	@RequestMapping("findSpecList")
	public List<Map> findSpecList(Long id){
		//查询模板对象
		List<Map> list = typeTemplateService.findSpecList(id);
		return list;

	}

}
