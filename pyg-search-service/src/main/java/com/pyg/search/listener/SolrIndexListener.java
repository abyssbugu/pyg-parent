package com.pyg.search.listener;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.TbItemMapper;
import com.pyg.pojo.TbItem;
import com.pyg.pojo.TbItemExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.List;

/**
 * Created by Abyss on 2018/7/1.
 * description:
 */
/**
 * 监听器： 接受消息
 * @author hubin
 * 1，接受消息，消息类型是集合
 * 2，根据数组查询审核通过，且已经上架的商品数据
 * 3，把商品数据设置到索引库即可实现索引库同步
 *
 */
public class SolrIndexListener implements MessageListener{


    //注入商品mapper接口代理对象
    @Autowired
    private TbItemMapper itemMapper;

    //注入solr模板对象
    @Autowired
    private SolrTemplate solrTemplate;


    @Override
    public void onMessage(Message message) {
        // TODO Auto-generated method stub
        if(message instanceof TextMessage) {
            try {
                TextMessage m = (TextMessage) message;
                //获取消息
                String ids =  m.getText();
                //把字符串转换成数组
                List<Long> goodsIds = JSON.parseArray(ids, Long.class);
                //根据货品id查询审核后，且已经上架的商家
                //创建example对象
                TbItemExample example = new TbItemExample();
                //创建criteria对象
                TbItemExample.Criteria createCriteria = example.createCriteria();
                //设置查询参数
                //必须是上架的商品
                createCriteria.andStatusEqualTo("1");

                //根据goodsIds查询
                createCriteria.andGoodsIdIn(goodsIds);

                //执行查询
                List<TbItem> list = itemMapper.selectByExample(example);

                solrTemplate.saveBeans(list);

                //提交
                solrTemplate.commit();

            } catch (JMSException e) {
                e.printStackTrace();
            }


        }
    }

}

