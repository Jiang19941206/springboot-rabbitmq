package com.jiangcl.springbootrabbitmq.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc 实现延迟消息
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitMQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/delay")
    public String delay(){
        rabbitTemplate.convertAndSend("delay.queue", "延时消息。。。");
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "发送");
        return "success";
    }
}
