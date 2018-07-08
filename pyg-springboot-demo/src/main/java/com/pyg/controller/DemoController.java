package com.pyg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pyg.pojo.TbItem;

@RestController
public class DemoController {
	
	//注入环境对象
	@Autowired
	private Environment env;
	
	/**
	 * 需求：springboot入门
	 */
	@RequestMapping("/hello1")
	public String showhello() {
		
		//获取环境值
		String url = env.getProperty("user.url");
		
		return "show hello hhhhhhhhhhh 自定义环境值："+url;
	}
	/**
	 * 需求：springboot返回对象
	 */
	@RequestMapping("/pojo")
	public TbItem showPojo() {
		
		//创建对象
		TbItem item = new TbItem();
		item.setId(1000000L);
		item.setTitle("牛逼");
		
		return item;
	}

}
