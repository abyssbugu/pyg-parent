package com.pyg.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.pojo.TbItem;
import com.pyg.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.HighlightEntry;
import org.springframework.data.solr.core.query.result.HighlightPage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class SearchServiceImpl implements SearchService {
	
	//注入solr模板对象
	@Autowired
	private SolrTemplate solrTemplate;


	/**
	 * 需求：根据关键词进行搜索 参数：String keywords 返回值：Map
	 */
	@Override
	public Map<String, Object> searchList(Map<String, Object> searchMap) {
		// 创建封装条件参数的query对象
		HighlightQuery query = new SimpleHighlightQuery();

		// 1，获取主查询条件
		String keywords = (String) searchMap.get("keywords");

		Criteria criteria = null;
		// 判断参数是否为null
		if (keywords != null && !"".equals(keywords)) {
			criteria = new Criteria("item_keywords").is(keywords);
		} else {
			criteria = new Criteria().expression("*:*");
		}
		// 添加查询条件
		query.addCriteria(criteria);

		// 2，分类过滤查询
		// 获取分类参数
		String category = (String) searchMap.get("category");

		// 判断分类过滤参数是否为空
		if (category != null && !"".equals(category)) {
			// 新建一个criteria对象
			Criteria filterCriteria = new Criteria("item_category").is(category);
			// 创建过滤对象
			FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
			// 把过滤对象添加到查询对象
			query.addFilterQuery(filterQuery);
		}

		// 3,品牌过滤查询
		// 从参数对象中获取品牌参数
		String brand = (String) searchMap.get("brand");
		// 判断品牌过滤参数是否为空
		if (brand != null && !"".equals(brand)) {
			// 新建一个criteria对象
			Criteria filterCriteria = new Criteria("item_brand").is(brand);
			// 创建过滤对象
			FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
			// 把过滤对象添加到查询对象
			query.addFilterQuery(filterQuery);
		}

		// 4,价格过滤查询
		// 从参数对象中获取价格参数
		// 0-500元 500-1000元 1000-1500元 1500-2000元 2000-3000元 3000-*元以上
		String price = (String) searchMap.get("price");
		// 判断价格参数是否存在
		if (price != null && !"".equals(price)) {
			// 切分价格参数
			String[] prices = price.split("-");

			// 判断起始价格是否为0
			if (!prices[0].equals("0")) {
				// 新建一个criteria对象
				// greaterThanEqual: >=
				Criteria filterCriteria = new Criteria("item_price").greaterThanEqual(prices[0]);
				// 创建过滤对象
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				// 把过滤对象添加到查询对象
				query.addFilterQuery(filterQuery);
			}

			// 判断是否是结束价格 *
			if (!prices[1].equals("*")) {
				// 新建一个criteria对象
				// lessThanEqual : <=
				Criteria filterCriteria = new Criteria("item_price").lessThanEqual(prices[1]);
				// 创建过滤对象
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				// 把过滤对象添加到查询对象
				query.addFilterQuery(filterQuery);
			}

		}

		// 5,规格属性参数过滤查询
		// 获取规格属性参数
		// spec = {"网络制式"："","内存"：“”}
		Map<String, String> specMap = (Map<String, String>) searchMap.get("spec");
		// 判断规格属性值是否存在
		if (specMap != null) {
			// 循环map对象，获取规格及规格属性值
			for (String key : specMap.keySet()) {

				Criteria filterCriteria = new Criteria("item_spec_" + key).is(specMap.get(key));
				// 创建过滤对象
				FilterQuery filterQuery = new SimpleFilterQuery(filterCriteria);
				// 把过滤对象添加到查询对象
				query.addFilterQuery(filterQuery);
			}

		}

		// 6,排序查询
		// 获取排序参数
		// 获取排序方法
		// asc desc
		String sort = (String) searchMap.get("sort");
		// 获取排序字段
		String sortField = (String) searchMap.get("sortField");

		// 判断排序方法，排序字段是否为空
		if (sort != null && !"".equals(sort) && sortField != null && !"".equals(sortField)) {
			// 判断排序方式
			if (sort.equals("ASC")) {
				// 创建排序对象
				Sort sort2 = new Sort(Sort.Direction.ASC, "item_" + sortField);
				// 把排序对象设置到查询对象
				query.addSort(sort2);
			}

			if (sort.equals("DESC")) {
				// 创建排序对象
				Sort sort2 = new Sort(Sort.Direction.DESC, "item_" + sortField);
				// 把排序对象设置到查询对象
				query.addSort(sort2);
			}
		}

		//7,高亮查询
		//创建高亮对象
		HighlightOptions hOptions = new HighlightOptions();
		//添加高亮字段
		hOptions.addField("item_title");
		//设置高亮前缀
		hOptions.setSimplePrefix("<font color='red'>");
		hOptions.setSimplePostfix("</font>");

		//把高亮添加到查询对象中
		query.setHighlightOptions(hOptions);

		//8,分页查询
		//获取分页参数
		Integer page = new Integer(searchMap.get("page")+"");
		//获取每页显示的条数
		Integer pageSize = new Integer(searchMap.get("pageSize")+"");

		//判断页码参数是否为空
		if(page==null) {
			page=1;
		}
		if(pageSize==null) {
			pageSize=30;
		}

		//计算查询的起始角标
		Integer startNo=(page-1)*pageSize;

		// 分页查询
		query.setOffset(startNo);
		query.setRows(pageSize);

		// 查询
		HighlightPage<TbItem> highlightPage = solrTemplate.queryForHighlightPage(query, TbItem.class);

		// 获取总记录数据
		long totalElements = highlightPage.getTotalElements();

		//获取总页码
		int totalPages = highlightPage.getTotalPages();

		// 获取总记录
		List<TbItem> list = highlightPage.getContent();

		//循环记录，获取高亮字段
		for (TbItem tbItem : list) {
			//获取高亮
			List<HighlightEntry.Highlight> highlights = highlightPage.getHighlights(tbItem);
			//判断高亮是否存在
			if(highlights!=null && highlights.size()>0) {
				//获取高亮字段
				String highLightTitle = highlights.get(0).getSnipplets().get(0);
				//把高亮字段添加到对象
				tbItem.setTitle(highLightTitle);
			}


		}

		// 创建map对象，封装查询结果
		Map<String, Object> maps = new HashMap<>();
		maps.put("total", totalElements);
		maps.put("totalPages", totalPages);
		maps.put("rows", list);

		return maps;
	}


}
