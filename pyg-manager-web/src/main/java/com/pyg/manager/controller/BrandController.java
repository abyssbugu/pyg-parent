package com.pyg.manager.controller;

import java.util.List;

import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.manager.service.BrandService;
import com.pyg.pojo.TbBrand;

@RestController
@RequestMapping("/brand")
public class BrandController {

	//注入远程service服务对象
	@Reference(timeout=10000000)
	private BrandService brandService;
	
	/**
	 * 需求：查询所有品牌数据
	 */
	@RequestMapping("findAll")
	public List<TbBrand> findAll(){
		//调用远程service服务对象方法
		List<TbBrand> list = brandService.findAll();
		return list;
	}

	/**
	 * 需求：分页查询
	 * 参数：
	 * Integer page, Integer rows
	 * 返回值：分页包装类对象
	 * PageResult
	 */
	@RequestMapping("findPage")
	public PageResult findPage(Integer page, Integer rows) {
		//调用远程service服务方法
		PageResult result = brandService.findPage(page,rows);
		return result;
	}

	/**
	 * 需求：添加品牌数据
	 * 特点：
	 * angularjs在前端负责对参数进行组装，然后进行传递。
	 * angularJS传递的参数全部是json格式数据对象
	 */
	@RequestMapping("add")
	public PygResult add(@RequestBody TbBrand brand) {
		//调用远程服务方法
		PygResult result = brandService.add(brand);
		return result;
	}

	/**
	 * 需求：根据id查询品牌数据，用于修改回显
	 */
	@RequestMapping("findOne")
	public TbBrand findOne(Long id) {
		TbBrand brand = brandService.findOne(id);
		return brand;
	}

	/**
	 * 需求：修改品牌数据
	 */
	@RequestMapping("update")
	public PygResult update(@RequestBody TbBrand brand) {
		PygResult result = brandService.updateByPrimaryKey(brand);
		return result;
	}

	/**
	 * 需求：批量删除
	 */
	@RequestMapping("delete")
	public PygResult delete(Long[] ids) {
		//调用远程service服务方法，实现删除
		PygResult result = brandService.delete(ids);

		return result;

	}
}
