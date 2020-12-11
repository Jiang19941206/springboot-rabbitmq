package com.jiangcl.springbootrabbitmq.config.topic;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc springboot route topic模型
 */
@Component
public class TopicConsumer {

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "boot-route-topic",type = ExchangeTypes.TOPIC),
            key = {"user.*","order.*"}
    )})
    public void consumer1(String message){
        System.out.println("consumer1--->" + message);
    }

    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,
            exchange = @Exchange(value = "boot-route-topic",type = ExchangeTypes.TOPIC),
            key = {"product.*","order.#"}
    )})
    public void consumer2(String message){
        System.out.println("consumer2--->" + message);
    }
}
