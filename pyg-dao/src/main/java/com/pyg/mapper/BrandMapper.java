package com.pyg.mapper;

import com.pyg.pojo.TbBrand;

import java.util.List;

/**
 * Created by Abyss on 2018/6/12.
 * description:
 */
public interface BrandMapper {
    /**
     * 需求：查询所有品牌数据
     */
     List<TbBrand> findAll();
    /**
     * 需求：添加品牌数据
     */
     void insert(TbBrand brand);
    /**
     * 需求：修改品牌
     */
     void updateByPrimaryKey(TbBrand brand);
    /**
     * 需求：根据id查询品牌数据，用于修改回显
     */
     TbBrand findOne(Long id);
    /**
     * 需求：根据id删除品牌数据
     */
     void delete(Long id);
}
