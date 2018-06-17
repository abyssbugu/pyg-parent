package com.pyg.manager.service;

import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;

import java.util.List;
import java.util.Map;

/**
 * Created by Abyss on 2018/6/12.
 * description:
 */
public interface BrandService {

    /**
     * 需求：查询所有品牌数据
     */
     List<TbBrand> findAll();

    /**
     * 需求：分页查询品牌列表
     *
     * @param page
     * @param rows
     * @return PageResult
     */
     PageResult findPage(Integer page, Integer rows);

    /**
     * 需求：添加品牌数据
     */
     PygResult add(TbBrand brand);

    /**
     * 需求：修改品牌
     */
     PygResult updateByPrimaryKey(TbBrand brand);

    /**
     * 需求：根据id查询品牌数据，用于修改回显
     */
     TbBrand findOne(Long id);

    /**
     * 需求：根据id删除品牌数据
     */
     PygResult delete(Long[] ids);

    List<Map> findBrandList();
}
