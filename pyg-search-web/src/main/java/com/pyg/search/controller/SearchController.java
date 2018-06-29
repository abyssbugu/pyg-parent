package com.pyg.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.search.service.SearchService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by Abyss on 2018/6/29.
 * description:
 */
@RestController
@RequestMapping("/search")
public class SearchController {

    //注入搜索服务对象
    @Reference(timeout = 100000)
    private SearchService searchService;

    /**
     * 需求：根据关键词进行搜索
     * 参数：String keywords
     * 返回值：Map
     */
    @RequestMapping("searchList")
    public Map<String, Object> searchList(@RequestBody Map<String, Object> searchMap) {
        //调用远程服务方法
        Map<String, Object> maps = searchService.searchList(searchMap);
        return maps;
    }

}
