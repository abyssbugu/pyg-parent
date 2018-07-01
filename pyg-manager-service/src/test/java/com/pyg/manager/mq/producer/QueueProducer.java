package com.pyg.manager.mq.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by Abyss on 2018/7/1.
 * description:
 */
public class QueueProducer {

    @Test
    public void sendMessage() throws Exception {

        String brokerURL = "tcp://192.168.72.150:61616";
        //创建mq连接工厂对象，连接消息服务器，指定activeMQ连接地址：协议，ip地址，端口
        ConnectionFactory cf = new ActiveMQConnectionFactory(brokerURL);
        //从工厂中获取连接
        Connection connection = cf.createConnection();
        //开启连接
        connection.start();

        //从连接对象中获取当前回话对象session
        //参数1：
        //1,true : 必须使用activemQ事务提交模式来发送及接受消息。参数2必须是：Session.SESSION_TRANSACTED
        //2,false：不使用activeMQ事务提交模式来接受消息。
        //参数2：
        //AUTO_ACKNOWLEDGE:自动确定模式，满足异步效果
        //CLIENT_ACKNOWLEDGE:客户端确认模式
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //开辟消息空间，且给消息空间指定一个命名名称
        Queue queue = session.createQueue("myQueue");

        //指定消息发送者producer,且指定往哪个消息空间发送
        MessageProducer producer = session.createProducer(queue);

        //创建消息对象，封装消息数据
        TextMessage message = new ActiveMQTextMessage();
        message.setText("唐僧师徒四人去西天取经?");

        //发送消息
        producer.send(message);

        //资源关闭
        producer.close();
        session.close();
        connection.close();





    }
}
