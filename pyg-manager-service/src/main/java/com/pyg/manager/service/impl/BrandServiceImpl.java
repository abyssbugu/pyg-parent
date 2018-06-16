package com.pyg.manager.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.BrandService;
import com.pyg.mapper.BrandMapper;
import com.pyg.mapper.TbBrandMapper;
import com.pyg.pojo.TbBrand;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    //注入品牌mapper接口代理对象
    @Autowired
    private TbBrandMapper tbBrandMapper;

    public List<TbBrand> findAll() {
        //调用接口方法
        List<TbBrand> list = tbBrandMapper.selectByExample(null);
        return list;
    }

    @Override
    public PageResult findPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);
        Page<TbBrand> pageInfo = (Page<TbBrand>) tbBrandMapper.selectByExample(null);
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    @Override
    public PygResult add(TbBrand brand) {
        PygResult result = new PygResult(false, "插入失败");
        try {
            tbBrandMapper.insert(brand);
            result.setSuccess(true);
            result.setMessage("插入成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 需求：修改品牌
     */
    @Override
    public PygResult updateByPrimaryKey(TbBrand brand) {
        // 修改
        try {
            // 修改品牌
            tbBrandMapper.updateByPrimaryKey(brand);
            // 修改成功
            return new PygResult(true, "修改成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new PygResult(false, "修改失败");
        }

    }

    /**
     * 需求：根据id查询品牌数据，用于修改回显
     */
    @Override
    public TbBrand findOne(Long id) {
        // 查询
        TbBrand brand = tbBrandMapper.selectByPrimaryKey(id);
        return brand;
    }

    /**
     * 需求：根据id删除品牌数据
     */
    @Override
    public PygResult delete(Long[] ids) {
        // 删除
        try {
            for (Long id : ids) {
                // 删除品牌
                tbBrandMapper.deleteByPrimaryKey(id);
            }
            // 删除成功
            return new PygResult(true, "删除成功");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return new PygResult(false, "删除失败");
        }

    }
}
