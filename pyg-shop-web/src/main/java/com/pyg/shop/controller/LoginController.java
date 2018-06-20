package com.pyg.shop.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Abyss on 2018/6/19.
 * description:
 */
@RestController
@RequestMapping("/login")
public class LoginController {
    @RequestMapping("loadLoginName")
    public Map showLoginName() {
        //直接从安全框架中获取用户登录名
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        HashMap<String, String> map = new HashMap<>();
        map.put("loginName", username);
        return map;
    }

}
