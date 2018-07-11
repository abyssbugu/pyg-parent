package com.pyg.user.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.pojo.TbUser;
import com.pyg.user.service.UserService;
import com.pyg.utils.PygResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Abyss on 2018/7/11.
 * description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    //注入服务对象
    @Reference(timeout=100000)
    private UserService userService;

    /**
     * 需求：完成用户注册
     * 参数：TbUser user,String smsCode
     * 返回值：pygResult
     */
    @RequestMapping("register")
    public PygResult register(String smsCode, @RequestBody TbUser user) {

        //调用服务层方法
        PygResult result = userService.register(smsCode, user);
        return result;
    }

    /**
     * 需求：接受手机号，把手机号发给activeMQ
     * 参数：String phone
     * 返回值：成功失败
     */
    @RequestMapping("getSmsCode")
    public PygResult createSmsCode(String phone) {
        //调用服务层方法
        PygResult result  = userService.createSmsCode(phone);
        return result;
    }

}
