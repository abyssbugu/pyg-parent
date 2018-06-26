package com.pyg.content.service.impl;

import java.util.List;

import com.pyg.constant.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pyg.content.service.ContentService;
import com.pyg.mapper.TbContentMapper;
import com.pyg.pojo.TbContent;
import com.pyg.pojo.TbContentExample;
import com.pyg.pojo.TbContentExample.Criteria;
import com.pyg.utils.PageResult;
import com.sun.org.apache.regexp.internal.recompile;

/**
 * 服务实现层
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;


    //注入redis模板对象
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 查询全部
     */
    @Override
    public List<TbContent> findAll() {
        return contentMapper.selectByExample(null);
    }

    /**
     * 按分页查询
     */
    @Override
    public PageResult findPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(null);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 增加
     * 添加广告数据时候，先删除缓存数据，同步缓存
     */
    @Override
    public void add(TbContent content) {
        //删除此分类的广告缓存
        redisTemplate.boundHashOps(RedisCache.INDEX_CACHE).delete(content.getCategoryId());

        contentMapper.insert(content);
    }

    /**
     * 修改
     */
    @Override
    public void update(TbContent content) {

        //根据id查询出原来对象
        TbContent content2 = contentMapper.selectByPrimaryKey(content.getId());

        //删除此分类的广告缓存
        redisTemplate.boundHashOps(RedisCache.INDEX_CACHE).delete(content2.getCategoryId());

        contentMapper.updateByPrimaryKey(content);
    }

    /**
     * 根据ID获取实体
     *
     * @param id
     * @return
     */
    @Override
    public TbContent findOne(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量删除
     */
    @Override
    public void delete(Long[] ids) {
        for (Long id : ids) {
            //根据id查询内容对象
            TbContent content = contentMapper.selectByPrimaryKey(id);
            //删除此分类的广告缓存
            redisTemplate.boundHashOps(RedisCache.INDEX_CACHE).delete(content.getCategoryId());

            contentMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public PageResult findPage(TbContent content, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        TbContentExample example = new TbContentExample();
        Criteria criteria = example.createCriteria();

        if (content != null) {
            if (content.getTitle() != null && content.getTitle().length() > 0) {
                criteria.andTitleLike("%" + content.getTitle() + "%");
            }
            if (content.getUrl() != null && content.getUrl().length() > 0) {
                criteria.andUrlLike("%" + content.getUrl() + "%");
            }
            if (content.getPic() != null && content.getPic().length() > 0) {
                criteria.andPicLike("%" + content.getPic() + "%");
            }
            if (content.getContent() != null && content.getContent().length() > 0) {
                criteria.andContentLike("%" + content.getContent() + "%");
            }
            if (content.getStatus() != null && content.getStatus().length() > 0) {
                criteria.andStatusLike("%" + content.getStatus() + "%");
            }

        }

        Page<TbContent> page = (Page<TbContent>) contentMapper.selectByExample(example);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 需求：根据分类id查询首页区域广告内容
     * 参数：Long categoryId
     * 返回值：List<TbContent>
     * 前台系统频繁读取广告数据： 从数据库读取，因此对数据库造成很大压力（io），因此减轻数据库压力。
     * 解决方案： 加缓存服务器（把频繁读取的数据放入缓存，这些数据的查询就不需要查询数据库了）
     * 添加缓存流程：
     * 1，查询广告数据时候，首先先查询redis服务器中缓存数据
     * 2，如果缓存中有数据，直接返回即可，不再查询数据库，大大减轻数据库压力
     * 3，如果缓存中没有数据，将会查询数据库，同时需要把数据放入缓存服务器，其他用户再次查询广告数据时，redis缓存中就有数据了。
     * 缓存数据结构：hash
     * key:INDEX_CACHE(首页缓存)  FOOD_CACHE(食品页面缓存)
     * field:categoryId(页面那个区域缓存)
     * value:缓存值
     * <p>
     * <p>
     * 今日推荐：
     * key:index_cache
     * filed：2
     * value:List<tbContent>
     */
    public List<TbContent> findAdList(Long categoryId) {


        try {
            //先查询缓存服务器
            List<TbContent> cacheList = (List<TbContent>) redisTemplate.boundHashOps(RedisCache.INDEX_CACHE).get(categoryId);
            //判断缓存是否存在
            if (cacheList != null && cacheList.size() > 0) {
                return cacheList;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 创建example对象
        TbContentExample example = new TbContentExample();
        //设置查询参数
        example.createCriteria()
        //设置状态
        .andCategoryIdEqualTo(categoryId)
                //设置广告排序
                .andStatusEqualTo("1");
        example.setOrderByClause("sort_order");

        //执行查询
        List<TbContent> list = contentMapper.selectByExample(example);

        //把缓存数据放入redis
        redisTemplate.boundHashOps(RedisCache.INDEX_CACHE).put(categoryId, list);

        return list;
    }

}
