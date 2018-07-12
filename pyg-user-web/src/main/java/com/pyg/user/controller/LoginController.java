package com.pyg.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	/**
	 * 需求：获取单点登录用户名
	 */
	@RequestMapping("loadLoginName")
	public Map<String, String> loadLoginName(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		//创建map对象
		Map<String, String> maps = new HashMap<>();
		maps.put("loginName", username);
		
		return maps;
	}

}
