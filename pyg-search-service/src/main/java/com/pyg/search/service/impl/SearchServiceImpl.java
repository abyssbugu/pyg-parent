package com.pyg.search.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pyg.mapper.TbItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.data.solr.core.query.result.HighlightPage;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	
	//注入solr模板对象
	@Autowired
	private SolrTemplate solrTemplate;


	/**
	 * 需求：根据关键词进行搜索
	 * 参数：String keywords 
	 * 返回值：Map
	 */
	public Map<String, Object> searchList(String keywords) {
		// 创建封装条件参数的query对象
		HighlightQuery query = new SimpleHighlightQuery();
		Criteria criteria = null;
		//判断参数是否为null
		if(keywords!=null && !"".equals(keywords)) {
			criteria = new Criteria("item_title").is(keywords);
		}else {
			criteria = new Criteria().expression("*:*");
		}
		
		//添加查询条件
		query.addCriteria(criteria);
		
		//分页查询
		query.setOffset(0);
		query.setRows(30);
		
		//查询
		HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);
		
		//获取总记录数据
		long totalElements = highlightPage.getTotalElements();
		
		//获取总记录
		List<TbItem> list = highlightPage.getContent();
		
		//创建map对象，封装查询结果
		Map<String, Object> maps = new HashMap<>();
		maps.put("total",totalElements);
		maps.put("rows", list);
		
		
		return maps;
	}

}
