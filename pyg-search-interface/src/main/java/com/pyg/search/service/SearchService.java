package com.pyg.search.service;

import java.util.Map;

public interface SearchService {
	/**
	 * 需求：根据关键词进行搜索
	 * 参数：String keywords 
	 * 返回值：Map
	 */
	Map<String, Object> searchList(String keywords);

}
