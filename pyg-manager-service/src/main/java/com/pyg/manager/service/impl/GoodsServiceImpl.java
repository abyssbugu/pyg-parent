package com.pyg.manager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pyg.mapper.*;
import com.pyg.pojo.*;
import com.pyg.utils.PygResult;
import com.pyg.vo.Goods;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.pojo.TbGoodsExample.Criteria;
import com.pyg.manager.service.GoodsService;

import com.pyg.utils.PageResult;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * 服务实现层
 *
 * @author Administrator
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private TbGoodsMapper goodsMapper;

    // 注入货品描述对象
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;

    // 注入商品mapper接口代理对象
    @Autowired
    private TbItemMapper itemMapper;

    // 注入品牌mapper
    @Autowired
    private TbBrandMapper brandMapper;

    // 注入商品分类mapper接口代理对象
    @Autowired
    private TbItemCatMapper itemCatMapper;

    // 注入商家mapper接口代理对象
    @Autowired
    private TbSellerMapper sellerMapper;

    //注入模板对象
    @Autowired
    private JmsTemplate jmsTemplate;

    //注入消息发送空间地址对象
    @Autowired
    private ActiveMQTopic activeMQTopic;


    /**
     * 查询全部
     */
    @Override
    public List<TbGoods> findAll() {
        return goodsMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     */
    @Override
    public void add(TbGoods goods) {
        goodsMapper.insert(goods);
    }


    /**
     * 修改
     */
    @Override
    public void update(TbGoods goods) {
        goodsMapper.updateByPrimaryKey(goods);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbGoods findOne(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
//            goodsMapper.deleteByPrimaryKey(id);

            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setIsDelete(null);
            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }


    @Override
    public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbGoodsExample example = new TbGoodsExample();
        Criteria criteria = example.createCriteria();

        if (goods != null) {
            if (goods.getSellerId() != null && goods.getSellerId().length() > 0) {
                criteria.andSellerIdLike("%" + goods.getSellerId() + "%");
            }
            if (goods.getGoodsName() != null && goods.getGoodsName().length() > 0) {
                criteria.andGoodsNameLike("%" + goods.getGoodsName() + "%");
            }
            if (goods.getAuditStatus() != null && goods.getAuditStatus().length() > 0) {
                criteria.andAuditStatusLike("%" + goods.getAuditStatus() + "%");
            }
            if (goods.getIsMarketable() != null && goods.getIsMarketable().length() > 0) {
                criteria.andIsMarketableLike("%" + goods.getIsMarketable() + "%");
            }
            if (goods.getCaption() != null && goods.getCaption().length() > 0) {
                criteria.andCaptionLike("%" + goods.getCaption() + "%");
            }
            if (goods.getSmallPic() != null && goods.getSmallPic().length() > 0) {
                criteria.andSmallPicLike("%" + goods.getSmallPic() + "%");
            }
            if (goods.getIsEnableSpec() != null && goods.getIsEnableSpec().length() > 0) {
                criteria.andIsEnableSpecLike("%" + goods.getIsEnableSpec() + "%");
            }
            if (goods.getIsDelete() != null && goods.getIsDelete().length() > 0) {
                criteria.andIsDeleteLike("%" + goods.getIsDelete() + "%");
            }

        }

        Page<TbGoods> page = (Page<TbGoods>) goodsMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加 添加商品数据 1，保存货品 2，保存货品描述 3，保存商品集合SKU 注意： 一个货品对应多个商品。
     * 在保存时候必须返回货品主键，在保存货品时候，把货品主键设置到商品外键中，实现关系维护。
     */
    @Override
    public void add(Goods goods) {
        // 1，配置货品TbGoodsMapper.xml返回主键
        // 2,从包装类对象中获取货品对象，实现保存
        TbGoods tbGoods = goods.getGoods();

        // 直接使用insert，其他字段如果没有值，默认值为NULL
        // insertSelective,自动对保存自动值进行非空判断，如果此字段为null,此字段不参与值得插入，此字段将会直接使用默认值。
        // 保存
        goodsMapper.insertSelective(tbGoods);

        // 3，保存货品描述表
        // 获取描述对象
        TbGoodsDesc goodsDesc = goods.getGoodsDesc();
        // 设置外键
        goodsDesc.setGoodsId(tbGoods.getId());
        // 保存
        goodsDescMapper.insertSelective(goodsDesc);

        // 保存商品表
        // 获取sku集合对象
        List<TbItem> itemList = goods.getItemList();

        // 获取是否启动规格属性值
        String isEnableSpec = tbGoods.getIsEnableSpec();

        // 判断是否启用规格
        if ("1".equals(isEnableSpec)) {
            // 循环sku集合，保存sku
            for (TbItem tbItem : itemList) {

                // 获取spec规格属性组合
                // {"网络":"电信2G","内存"：“”}
                String spec = tbItem.getSpec();
                // 把规格属性组合转换成map对象
                Map<String, String> specs = (Map<String, String>) JSON.parse(spec);

                // 定义空值，封装规格属性值
                String specValue = "";

                // 循环获取规格值
                for (String key : specs.keySet()) {
                    specValue += specs.get(key);
                }
                // 标题
                // spu标题+sku规格属性组合
                tbItem.setTitle(tbGoods.getGoodsName() + "+" + specValue);

                // 添加sku商品基本属性数据
                this.createItem(tbGoods, tbItem, goodsDesc);

                // 保存
                itemMapper.insertSelective(tbItem);

            }
        } else {

            // 创建一个商品对象
            TbItem tbItem = new TbItem();
            // 标题
            tbItem.setTitle(tbGoods.getGoodsName());
            // 设置价格
            tbItem.setPrice(tbGoods.getPrice());
            // 库存数量
            tbItem.setNum(9999999);
            // 设置是否启用
            tbItem.setStatus("0");
            // 设置是否默认
            tbItem.setIsDefault("0");

            // 设置商品属性
            this.createItem(tbGoods, tbItem, goodsDesc);

            // 保存
            itemMapper.insertSelective(tbItem);

        }

    }

    private void createItem(TbGoods tbGoods, TbItem tbItem, TbGoodsDesc goodsDesc) {
        // TODO Auto-generated method stub
        // 买点
        tbItem.setSellPoint(tbGoods.getCaption());

        // 商品图片设置
        // 从商品描述表中获取图片，把图片保存到sku表中
        // [{"color":"蓝色","url":"http://192.168.66.67/group1/M00/00/02/wKhCQ1qzBHCABs3xAA1rIuRd3Es100.jpg"},{"color":"黑色","url":"http://192.168.66.67/group1/M00/00/02/wKhCQ1qzBHmAVteGAAvea_OGt2M066.jpg"}]
        String itemImages = goodsDesc.getItemImages();
        // 把字符转换成对象
        List<Map> imagesList = JSON.parseArray(itemImages, Map.class);

        String url = "";
        // 判断是否为null
        if (imagesList != null && imagesList.size() > 0) {
            url = (String) imagesList.get(0).get("url");

        }
        // 添加图片地址
        tbItem.setImage(url);
        // 分类id
        tbItem.setCategoryid(tbGoods.getCategory3Id());

        // 时间
        Date date = new Date();
        tbItem.setUpdateTime(date);
        tbItem.setCreateTime(date);

        // 实际花费场价
        tbItem.setCostPirce(tbItem.getPrice());

        // 市场价
        tbItem.setMarketPrice(tbItem.getPrice());

        // spu id
        tbItem.setGoodsId(tbGoods.getId());

        // sellerId
        tbItem.setSellerId(tbGoods.getSellerId());

        // 品牌名称
        TbBrand brand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
        tbItem.setBrand(brand.getName());
        // 分类名称
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
        tbItem.setCategory(itemCat.getName());
        // 商家名称
        TbSeller seller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
        tbItem.setSeller(seller.getNickName());

    }




    /**
     * 需求：审核商家状态
     * 0,未审核
     * 1，审核通过
     * 2，未通过
     * 3，关闭
     * 运营商审核：
     * 1，发送goodsIds作为mq消息
     * 2,搜索服务接受消息
     * 3，搜索服务根据goodids查询审核后的商家，且已经是上架的商品。
     * 4，搜索服务把查询出的商品设置到索引库即可实现索引库同步。
     */
    public PygResult updateStatus(Long[] ids, String status) {
        try {
            //创建集合对象
            List<Long> goodsIds = new ArrayList<Long>();
            //循环id数组
            for (Long id : ids) {
                //根据id查询货品对象
                TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
                //设置状态
                tbGoods.setAuditStatus(status);

                //修改
                goodsMapper.updateByPrimaryKeySelective(tbGoods);

                goodsIds.add(id);
            }


            //使用消息模板对象，发送消息
            jmsTemplate.send(activeMQTopic, new MessageCreator() {

                @Override
                public Message createMessage(Session session) throws JMSException {
                    return session.createTextMessage(goodsIds.toString());
                }
            });

            //修改成功
            return new PygResult(true, "修改成功");

        } catch (Exception e) {
            e.printStackTrace();
            //修改失败
            return new PygResult(false, "修改失败");
        }
    }


}
