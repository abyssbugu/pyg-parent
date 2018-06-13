package com.pyg.manager.service.impl;

import com.pyg.manager.service.BrandService;
import com.pyg.mapper.BrandMapper;
import com.pyg.pojo.TbBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
	
	//注入品牌mapper接口代理对象
	@Autowired
	private BrandMapper brandMapper;

	public List<TbBrand> findAll() {
		//调用接口方法
		List<TbBrand> list = brandMapper.findAll();
		return list;
	}

}
