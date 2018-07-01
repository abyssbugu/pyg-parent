package com.pyg.search.mq.receiver;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Abyss on 2018/7/1.
 * description:
 */
public class SpringMQReciver {

    /**
     * 需求：发布订阅，及 点对点
     * @throws Exception
     */
    @Test
    public void receiveMessage() throws Exception {
        // 加载spring配置文件
        ApplicationContext app = new ClassPathXmlApplicationContext(
                "classpath*:spring/applicationContext-mq-consumer.xml");
        System.in.read();
    }

}