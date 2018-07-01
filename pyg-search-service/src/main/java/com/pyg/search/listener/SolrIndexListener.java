package com.pyg.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by Abyss on 2018/7/1.
 * description:
 */
public class SolrIndexListener implements MessageListener {

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
            System.out.println("接受消息:"+text);

        }
    }

}

