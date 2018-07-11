package com.pyg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MQController {


    //注入发送消息模板对象
    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    /**
     * 需求：發送消息
     */
    @RequestMapping("send/{m}")
    public String sendMessage(@PathVariable String m) {
        jmsTemplate.convertAndSend("itcast", m);
        return "消息发送成功";
    }


    @RequestMapping("/sendsms")
    public void sendSms() {
        Map map = new HashMap<>();
        map.put("phone", "13429847337");
        map.put("template_code", "SMS_139435146");
        map.put("sign_name", "龚世晟");
        map.put("code", "102931");
        jmsTemplate.convertAndSend("smsCodeQueue", map);
    }

    /**
     * 需求：接受消息
     */
    @JmsListener(destination = "itcast")
    public void recMessage(String message) {
        System.out.println("接受的消息是:" + message);
    }

}
