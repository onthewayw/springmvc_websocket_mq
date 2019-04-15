package com.wang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @Package $
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-15 11:34
 **/
@Component
public class PulishService {
    @Autowired(required = false)
    private StringRedisTemplate redisTemplate;
    public void publish(String channel, Object message){
        //该方法封装的 connection.publish(rawChannel, rawMessage);
        redisTemplate.convertAndSend(channel,message);
    }
}
