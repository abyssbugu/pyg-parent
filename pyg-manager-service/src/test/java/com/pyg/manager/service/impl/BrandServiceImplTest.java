package com.pyg.manager.service.impl;

import com.pyg.manager.service.BrandService;
import com.pyg.mapper.BrandMapper;
import com.pyg.pojo.TbBrand;
import org.apache.ibatis.session.RowBounds;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Abyss on 2018/6/13.
 * description:
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/applicationContext-dao.xml")
public class BrandServiceImplTest {


    @Autowired
    private BrandMapper brandMapper;

    @Test
    public void findAll() {
        List<TbBrand> brands = brandMapper.findAll();
        for (TbBrand brand : brands) {
            System.out.println(brand.getFirstChar());
        }
    }
}