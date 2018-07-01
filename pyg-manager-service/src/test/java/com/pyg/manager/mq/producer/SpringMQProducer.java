package com.pyg.manager.mq.producer;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by Abyss on 2018/7/1.
 * description:
 */
public class SpringMQProducer {

    /**
     * 需求：点对点消息发送
     */
    @Test
    public void sendMessageByPTP() {
        // 加载spring配置文件
        ApplicationContext app = new ClassPathXmlApplicationContext(
                "classpath*:spring/applicationContext-mq-producer.xml");
        //获取发送消息模板对象
        JmsTemplate jmsTemplate = app.getBean(JmsTemplate.class);

        //获取目的地对象
        ActiveMQQueue queue = app.getBean(ActiveMQQueue.class);

        //发送消息
        jmsTemplate.send(queue, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("猪八戒在高老庄学习java!");
            }
        });

    }

    /**
     * 需求：发布订阅模式消息发送
     */
    @Test
    public void sendMessageByPS() {
        // 加载spring配置文件
        ApplicationContext app = new ClassPathXmlApplicationContext(
                "classpath*:spring/applicationContext-mq-producer.xml");
        //获取发送消息模板对象
        JmsTemplate jmsTemplate = app.getBean(JmsTemplate.class);

        //获取目的地对象
        ActiveMQTopic topic = app.getBean(ActiveMQTopic.class);

        //发送消息
        jmsTemplate.send(topic, new MessageCreator() {

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("猪八戒在高老庄学习java!还学习大数据?");
            }
        });

    }

}