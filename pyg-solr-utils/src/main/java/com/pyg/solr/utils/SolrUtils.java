package com.pyg.solr.utils;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Abyss on 2018/6/29.
 * description:
 */
@Component
public class SolrUtils {

    //注入mapper接口代理对象
    @Autowired
    private TbItemMapper itemMapper;
    //注入solr模板
    @Autowired
    private SolrTemplate solrTemplate;


    /**
     * 需求：
     * 1，查询数据库
     * 2，把数据导入索引库
     */
    public void importDataToSolrIndex() {

        //创建example对象
        TbItemExample example = new TbItemExample();
        //创建criteria对象
        TbItemExample.Criteria createCriteria = example.createCriteria();
        //设置查询参数
        createCriteria.andStatusEqualTo("1");

        //查询数据库所有数据
        List<TbItem> list = itemMapper.selectByExample(example);

        //动态域
        for (TbItem tbItem : list) {
            //获取规格属性值
            //{"网络制式"：""}
            String spec = tbItem.getSpec();
            //把规格值转换成map对象
            Map<String, String> specMap = (Map<String, String>) JSON.parse(spec);
            //把规格属性添加到动态域字段
            tbItem.setSpecMap(specMap);


        }

        solrTemplate.saveBeans(list);

        //提交
        solrTemplate.commit();


    }

    public static void main(String[] args) {
        //加载配置文件
        ApplicationContext app = new ClassPathXmlApplicationContext("classpath*:spring/*.xml");
        //获取工具类对象
        SolrUtils solrUtils = app.getBean(SolrUtils.class);
        //调用数据导入方法
        solrUtils.importDataToSolrIndex();

    }

}