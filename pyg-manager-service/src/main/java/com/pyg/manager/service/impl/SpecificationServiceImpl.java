package com.pyg.manager.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.manager.service.SpecificationService;
import com.pyg.mapper.TbSpecificationMapper;
import com.pyg.mapper.TbSpecificationOptionMapper;
import com.pyg.pojo.TbSpecification;
import com.pyg.pojo.TbSpecificationExample;
import com.pyg.pojo.TbSpecificationOption;
import com.pyg.pojo.TbSpecificationOptionExample;
import com.pyg.pojo.TbSpecificationOptionExample.Criteria;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Specification;

@Service
public class SpecificationServiceImpl implements SpecificationService {

    // 注入规格mapper接口代理对象
    @Autowired
    private TbSpecificationMapper specificationMapper;

    /**
     * 需求：查询分页类别 参数： Integer page,Interger rows 返回值： PageResult
     */
    public PageResult findPage(Integer page, Integer rows) {
        // 设置分页查询参数
        PageHelper.startPage(page, rows);
        // 执行查询
        Page<TbSpecification> pageInfo = (Page<TbSpecification>) specificationMapper.selectByExample(null);
        // 返回分页包装类对象
        return new PageResult(pageInfo.getTotal(), pageInfo.getResult());
    }

    // 注入规格选项mapper接口代理对象
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    /**
     * 需求：保存规格，保存规格选项 参数：Specification 返回值：PygResult
     */
    public PygResult add(Specification specification) {
        try {
            // 首先先保存规格数据，返回主键，以便于维护规格选项的关系
            specificationMapper.insertSelective(specification.getTbSpecification());

            // 再保存规格选项，把规格的主键设置到外键中进行保存
            // 获取规格选项对象
            List<TbSpecificationOption> optionList = specification.getOptionList();
            // 循环保存规格选项
            for (TbSpecificationOption tbSpecificationOption : optionList) {
                // 设置外键
                tbSpecificationOption.setSpecId(specification.getTbSpecification().getId());
                // 保存
                specificationOptionMapper.insertSelective(tbSpecificationOption);

            }

            // 保存成功
            return new PygResult(true, "保存成功");

        } catch (Exception e) {
            e.printStackTrace();
            // 保存失败
            return new PygResult(false, "保存失败");
        }

    }

    /**
     * 需求：根据id查询规格对象 参数：Long id 返回值：TbSpecification
     */
    public Specification findOne(Long id) {
        // 创建包装类对象
        Specification specification = new Specification();

        // 根据主键查询
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        specification.setTbSpecification(tbSpecification);
        // 根据外键把规格选项查询
        // 创建规格选项example对象
        TbSpecificationOptionExample example = new TbSpecificationOptionExample();
        // 创建criteria对象
        Criteria createCriteria = example.createCriteria();
        // 封装外键参数
        createCriteria.andSpecIdEqualTo(id);
        // 执行查询
        List<TbSpecificationOption> optionList = specificationOptionMapper.selectByExample(example);
        specification.setOptionList(optionList);
        return specification;
    }

    /**
     * 需求：修改规格及规格选项 参数：Specification 返回值：PygResult
     */
    public PygResult update(Specification specification) {
        try {
            // 获取规格对象
            TbSpecification tbSpecification = specification.getTbSpecification();
            // 直接修改即可
            specificationMapper.updateByPrimaryKeySelective(tbSpecification);

            // 根据外键删除规格选项，再重新添加规格选项值
            // 创建规格选项example对象
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            // 创建criteria对象
            Criteria createCriteria = example.createCriteria();
            // 封装外键参数
            createCriteria.andSpecIdEqualTo(tbSpecification.getId());
            // 执行删除
            specificationOptionMapper.deleteByExample(example);

            // 获取规格选项集合对象
            List<TbSpecificationOption> optionList = specification.getOptionList();
            // 循环添加集合
            for (TbSpecificationOption tbSpecificationOption : optionList) {
                // 设置外键
                tbSpecificationOption.setSpecId(tbSpecification.getId());
                // 保存
                specificationOptionMapper.insertSelective(tbSpecificationOption);
            }

            // 返回添加成功
            return new PygResult(true, "修改成功");

        } catch (Exception e) {
            e.printStackTrace();
            // 添加失败
            return new PygResult(false, "修改失败");
        }
    }

    /**
     * 需求：删除规格及规格选项 参数：Long[] ids 返回值：PygResult
     */
    public PygResult delete(Long[] ids) {
        try {
            // 循环数组id
            for (Long id : ids) {
                // 根据id删除规格
                specificationMapper.deleteByPrimaryKey(id);
                // 根据外键删除对应规格选项
                // 创建规格选项example对象
                TbSpecificationOptionExample example = new TbSpecificationOptionExample();
                // 创建criteria对象
                Criteria createCriteria = example.createCriteria();
                // 封装外键参数
                createCriteria.andSpecIdEqualTo(id);
                // 删除规格选项
                specificationOptionMapper.deleteByExample(example);
            }

            // 删除成功
            return new PygResult(true, "删除成功");

        } catch (Exception e) {
            e.printStackTrace();
            // 删除失败
            return new PygResult(false, "删除失败");
        }
    }

    /**
     * 需求：查询规格表数据，进行多项选择
     * 返回值：List<Map>
     */
    public List<Map> findSpecList() {
        // 调用规格mapper接口代理对象
        List<Map> list = specificationMapper.findSpecList();
        return list;
    }

}
