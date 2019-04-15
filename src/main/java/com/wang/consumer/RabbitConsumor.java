package com.wang.consumer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * @Package com.zuma.consumer$
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-13 13:46
 **/
public class RabbitConsumor implements MessageListener {
    @Override
    public void onMessage(Message message) {
        System.out.println("consumer:"+message.toString());
    }
}
