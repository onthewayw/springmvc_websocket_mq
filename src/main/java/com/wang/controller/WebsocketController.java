package com.wang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Package $
 * @description:
 * @author: wangjiangtao
 * @create: 2019-04-15 14:14
 **/
@Controller
@RequestMapping("webSocket")
public class WebsocketController {
    @RequestMapping("/index/{topic}/{username}")
    public ModelAndView index(@PathVariable("topic") String topic, @PathVariable("username") String username) {
        ModelAndView mv = new ModelAndView("/websocket_1");
        mv.addObject("port", 8081);
        mv.addObject("topic", topic);
        mv.addObject("username", username);
        return mv;
    }
}
