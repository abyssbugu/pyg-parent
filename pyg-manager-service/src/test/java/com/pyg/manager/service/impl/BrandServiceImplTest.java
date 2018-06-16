package com.pyg.manager.service.impl;

import com.pyg.mapper.BrandMapper;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by Abyss on 2018/6/13.
 * description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/applicationContext-dao.xml")
public class BrandServiceImplTest {

    @Autowired
    private BrandMapper brandMapper;
    @Autowired
    private TbBrandMapper tbBrandMapper;


    @Test
    public void findAll() {
        List<TbBrand> brands = brandMapper.findAll();
        for (TbBrand brand : brands) {
            System.out.println(brand.getFirstChar());
        }
    }
    @Test
    public void tbFindAll() {
        List<TbBrand> tbBrands = tbBrandMapper.selectByExample(null);
        for (TbBrand tbBrand : tbBrands) {
            System.out.println(tbBrand.getFirstChar());

        }
    }
}