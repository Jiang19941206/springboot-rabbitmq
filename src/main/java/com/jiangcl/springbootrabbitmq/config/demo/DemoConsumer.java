package com.jiangcl.springbootrabbitmq.config.demo;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc springboot这和rabbit简单示例
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "boot-demo"))
public class DemoConsumer {

    @RabbitHandler
    public void demoConsumer(String message){
        System.out.println( System.currentTimeMillis() + " 消费消息" + message);
    }
}
