package com.wang.service;

/**
 * @Package com.wang.service$
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-13 13:33
 **/
public interface RabbitProductorService {
    void sendMessage(String exchange,String routeKey, Object message);
}
