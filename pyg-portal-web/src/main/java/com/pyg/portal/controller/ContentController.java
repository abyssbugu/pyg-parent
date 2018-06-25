package com.pyg.portal.controller;


import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.content.service.ContentService;
import com.pyg.pojo.TbContent;


/**
 * Created by Abyss on 2018/6/25.
 * description:
 */
@RestController
@RequestMapping("/content")
public class ContentController {

    //注入广告内容服务
    @Reference(timeout = 100000)
    private ContentService contentService;

    /**
     * 需求：根据分类id查询首页区域广告内容
     * 参数：Long categoryId
     * 返回值：List<TbContent>
     */
    @RequestMapping("findAdList")
    public List<TbContent> findAdList(Long categoryId) {
        //调用服务层方法
        List<TbContent> list = contentService.findAdList(categoryId);
        return list;
    }

}
