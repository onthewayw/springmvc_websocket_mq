/*
package com.wang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

*/
/**
 * @Package $
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-15 13:10
 **//*

@Configuration
public class RedisConfig {
    @Autowired(required = false)
    private JedisConnectionFactory jedisConnectionFactory;
    */
/**
     * 需要手动注册RedisMessagwListenerContainer加入IOC容器
     * *//*

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory);
        return container;
    }
}
*/
