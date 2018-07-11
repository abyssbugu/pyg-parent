package com.pyg.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pyg.mapper.TbUserMapper;
import com.pyg.pojo.TbUser;
import com.pyg.user.service.UserService;
import com.pyg.utils.PhoneFormatCheckUtils;
import com.pyg.utils.PygResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.JmsException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Abyss on 2018/7/11.
 * description:
 */
@Service
public class UserServiceImpl implements UserService {

    //注入用户mapper接口代理对象
    @Autowired
    private TbUserMapper uesrMapper;


    //注入redis模板对象，操作redis服务
    @Autowired
    private RedisTemplate<String, ?> redisTemplate;

    //注入消息发送模板对象
    @Autowired
    private JmsTemplate jmsTemplate;

    //注入模板
    @Value("${template_code}")
    private String template_code;

    //注入签名
    @Value("${sign_name}")
    private String sign_name;

    /**
     * 需求：完成用户注册
     * 参数：TbUser user,String smsCode
     * 返回值：pygResult
     * 业务流程：
     * 1，判断手机号格式是否正确
     * 2，判断验证码是否匹配
     * 	1)从redis服务器中获当前手机号取验证码
     *  2)把输入的验证码和redis服务器中验证码进行匹配，如果成功，验证码正确，否则验证码错误。
     * 3，密码加密
     * 4，完成注册
     */
    public PygResult register(String smsCode, TbUser user) {




        try {
            // 1，判断手机号格式是否正确
            if(!PhoneFormatCheckUtils.isChinaPhoneLegal(user.getPhone())) {
                return new PygResult(false, "手机号码格式不正确");
            };

            //2，判断验证码是否匹配
            //获取redis服务器中验证码
            String code = (Long) redisTemplate.boundHashOps("smsCode").get(user.getPhone())+"";

            //判断验证码是否相匹配
            if(!smsCode.equals(code)) {
                return new PygResult(false, "验证码不正确");
            }


            //3，密码加密
            String newPwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(newPwd);

            //补全时间
            Date date = new Date();
            user.setUpdated(date);
            user.setCreated(date);

            //完成注册
            uesrMapper.insertSelective(user);
            //注册成功
            return new PygResult(true, "注册成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new PygResult(false, "注册失败");
        }
    }



    /**
     * 需求：接受手机号，把手机号发给activeMQ
     * 参数：String phone
     * 返回值：成功失败
     * 业务流程：
     * 1，生成验证码
     * 2，把验证码保存redis服务器中，以便于在注册时进行验证验证码是否正确。
     * 3，把验证码，和手机号作为消息发送给activeMQ消息服务器。
     */
    public PygResult createSmsCode(String phone) {
        // 1，生成验证码 ，验证码是6位的数字
        try {
            //0.1324324324343545
            long code = (long)(Math.random()*1000000);

            //2，把验证码保存redis服务器中，以便于在注册时进行验证验证码是否正确。
            redisTemplate.boundHashOps("smsCode").put(phone, code);

            //设置过期时间
            redisTemplate.boundHashOps("smsCode").expire(5, TimeUnit.MINUTES);

            //创建map对象
            Map<String,String> maps = new HashMap<>();
            maps.put("phone", phone);
            maps.put("code", code+"");
            //模板号
            maps.put("template_code", template_code);
            //签名
            maps.put("sign_name", sign_name);

            //3，把验证码，和手机号作为消息发送给activeMQ消息服务器。
            jmsTemplate.convertAndSend("smsCodeQueue", maps);

            return new PygResult(true,"发送成功");
        } catch (JmsException e) {
            e.printStackTrace();
            return new PygResult(false,"发送失败");
        }
    }

}
