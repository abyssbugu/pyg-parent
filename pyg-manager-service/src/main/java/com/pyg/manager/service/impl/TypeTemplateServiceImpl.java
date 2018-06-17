package com.pyg.manager.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.TypeTemplateService;
import com.pyg.mapper.TbTypeTemplateMapper;
import com.pyg.pojo.TbTypeTemplate;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {

	// 注入模板mapper接口代理对象
	@Autowired
	private TbTypeTemplateMapper typeTemplateMapper;

	/**
	 * 需求：查询分页类别 参数： Integer page,Interger rows 返回值： PageResult
	 */
	public PageResult findPage(Integer page, Integer rows) {
		// 设置分页参数
		PageHelper.startPage(page, rows);
		// 分页查询
		Page<TbTypeTemplate> pageInfo = (Page<TbTypeTemplate>) typeTemplateMapper.selectByExample(null);
		// 返回分页包装类对象
		return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
	}

	/**
	 * 需求：保存模板数据 参数：TbTypeTemplate 返回值：PygResult
	 */
	public PygResult add(TbTypeTemplate typeTemplate) {
		try {
			// 实现保存
			typeTemplateMapper.insertSelective(typeTemplate);
			// 保存成功
			return new PygResult(true, "保存成功");
		} catch (Exception e) {
			e.printStackTrace();
			// 保存失败
			return new PygResult(false, "保存失败");
		}
	}

	/**
	 * 需求：根据id查询模板对象
	 * 参数：Long id
	 * 返回值：TbTypeTemplate
	 */
	public TbTypeTemplate findOne(Long id) {
		// 
		TbTypeTemplate typeTemplate = typeTemplateMapper.selectByPrimaryKey(id);
		
		return typeTemplate;
	}

	/**
	 * 需求：修改模板
	 * 参数：TbTypeTemplate
	 * 返回值：PygResult
	 */
	public PygResult update(TbTypeTemplate typeTemplate) {
		try {
			// 
			typeTemplateMapper.updateByPrimaryKeySelective(typeTemplate);
			//修改成功
			return new PygResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false, "修改失败");
		}
	}

	/**
	 * 需求：删除模板
	 * 参数：Long[] ids
	 * 返回值：PygResult
	 */
	public PygResult delete(Long[] ids) {
		try {
			// 循环
			for (Long id : ids) {
				
				typeTemplateMapper.deleteByPrimaryKey(id);
			}
			
			//删除成功
			return new PygResult(true, "删除成功");
			
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false, "删除失败");
		}
	}

}
