package com.pyg.search.mq.receiver;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by Abyss on 2018/7/1.
 * description:
 */
public class TopicReceiver {
    @Test
    public void receiverMessage() throws Exception {

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

        //指定消息空间地址
        Topic topic = session.createTopic("myTopic");

        //指定消息消费者，且指定消费消息空间地址
        MessageConsumer consumer = session.createConsumer(topic);

        //使用监听模式消费消息
        consumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                if(message instanceof TextMessage) {
                    TextMessage m = (TextMessage) message;
                    //获取消息
                    String text = null;
                    try {
                        text = m.getText();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }

                    System.out.println("订阅模式接受消息:"+text);

                }
            }
        });

        System.in.read();

        //资源关闭
        consumer.close();
        session.close();
        connection.close();





    }
}
