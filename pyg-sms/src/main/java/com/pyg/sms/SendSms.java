package com.pyg.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.pyg.utils.SmsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SendSms {

    // 注入工具类对象
    @Autowired
    private SmsUtils smsUtils;

    @JmsListener(destination = "smsCodeQueue")
    public void sendSms(Map<String, String> smsMap) {
        try {
            // 获取消息
            // 模板
            String templateCode = smsMap.get("template_code");
            // 签名
            String signName = smsMap.get("sign_name");
            // 模板
            String phone = smsMap.get("phone");
            // 模板
            String code = smsMap.get("code");
            // 调用发送短信方法
            SendSmsResponse sendSmsResponse = smsUtils.sendSms(signName, templateCode, phone, code);
            System.out.println(sendSmsResponse);
            System.out.println(sendSmsResponse.getCode());
            System.out.println(sendSmsResponse.getMessage());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
