package com.wang.server;

import com.wang.listener.SubscribeListener;
import com.wang.service.PulishService;
import com.wang.util.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Package $
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-15 13:43
 **/
@ServerEndpoint(value = "/im_webSocket/{topic}/{username}", configurator = SpringConfigurator.class)
public class WebsocketEndpoint {
    /**
     * 因为@ServerEndpoint不支持注入，所以使用SpringUtils获取IOC实例
     */
    //private StringRedisTemplate redisTemplate = SpringUtils.getBean(StringRedisTemplate.class);

    // private RedisMessageListenerContainer redisMessageListenerContainer = SpringUtils.getBean(RedisMessageListenerContainer.class);
    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;

    @Autowired(required = false)
    private RedisMessageListenerContainer redisMessageListenerContainer;

    @Autowired(required = false)
    private PulishService pulishService;

    //存放该服务器该ws的所有连接。用处：比如向所有连接该ws的用户发送通知消息。
    private static CopyOnWriteArraySet<WebsocketEndpoint> sessions = new CopyOnWriteArraySet<>();

    private Session session;

    @OnOpen
    public void onOpen(Session session, @PathParam("topic") String topic) {
        System.out.println("java websocket:打开连接:topic------" + topic);
        this.session = session;
        sessions.add(this);
        SubscribeListener subscribeListener = new SubscribeListener();
        subscribeListener.setSession(session);
        subscribeListener.setRedisTemplate(redisTemplate);
        //设置订阅topic
        try {
            redisMessageListenerContainer.addMessageListener(subscribeListener, new ChannelTopic(topic));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @OnMessage
    public void onMessage(Session session, String message, @PathParam("topic") String topic, @PathParam("username") String username) {
        System.out.println("websocket 收到消息：" + message);
        //PulishService pulishService = SpringUtils.getBean(PulishService.class);
        pulishService.publish(topic, message);
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("java websocket:关闭连接");
        sessions.remove(this);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("java websocket 出现错误");
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
