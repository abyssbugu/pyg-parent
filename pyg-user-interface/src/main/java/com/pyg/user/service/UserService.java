package com.pyg.user.service;

import com.pyg.pojo.TbUser;
import com.pyg.utils.PygResult;

/**
 * Created by Abyss on 2018/7/11.
 * description:
 */
public interface UserService {

    /**
     * 需求：完成用户注册
     * 参数：TbUser user,String smsCode
     * 返回值：pygResult
     */
    PygResult register(String smsCode, TbUser user);

    /**
     * 需求：接受手机号，把手机号发给activeMQ
     * 参数：String phone
     * 返回值：成功失败
     * 业务流程：
     * 1，生成验证码
     * 2，把验证码保存redis服务器中，以便于在注册时进行验证验证码是否正确。
     * 3，把验证码，和手机号作为消息发送给activeMQ消息服务器。
     */
    PygResult createSmsCode(String phone);

}