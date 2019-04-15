package com.wang.service.impl;

import com.wang.service.RabbitProductorService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Package $
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-13 13:30
 **/
@Service
@Transactional
public class RabbitProductorServiceImpl implements RabbitProductorService {
    @Autowired(required = false)
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String exchange, String routeKey, Object message) {
        try {
            rabbitTemplate.convertAndSend(exchange,routeKey,message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
