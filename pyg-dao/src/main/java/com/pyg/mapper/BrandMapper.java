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
}