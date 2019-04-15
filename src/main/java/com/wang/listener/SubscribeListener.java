package com.wang.listener;

/**
 * @Package $
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-15 11:37
 **/

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.websocket.Session;
import java.io.IOException;

/**
 * 订阅监听类
 * */
public class SubscribeListener implements MessageListener {

    private final Logger logger = LoggerFactory.getLogger(SubscribeListener.class);

    private StringRedisTemplate redisTemplate;

    private Session session;

    public Logger getLogger() {
        return logger;
    }

    public StringRedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String msg = new String(message.getBody());
        logger.info(new String(pattern) + "主题发布：" + msg);
        if(null!=session){
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
