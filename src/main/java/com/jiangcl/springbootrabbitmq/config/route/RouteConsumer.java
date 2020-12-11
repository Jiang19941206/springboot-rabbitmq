package com.jiangcl.springbootrabbitmq.config.route;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc springboot route direct模型
 *      订阅模型：消费者只能消费指定路由下的消息
 */
@Component
public class RouteConsumer {

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "boot-route-direct",type = ExchangeTypes.DIRECT),
                    key = {"info","error"})
    })
    public void consumer1(String message){
        System.out.println("consumer1--->" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "boot-route-direct",type = ExchangeTypes.DIRECT),
                    key = {"error","warning"})
    })
    public void consumer2(String message){
        System.out.println("consumer2--->" + message);
    }

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue,
                    exchange = @Exchange(value = "boot-route-direct",type = ExchangeTypes.DIRECT),
                    key = {"info","warning"})
    })
    public void consumer3(String message){
        System.out.println("consumer3--->" + message);
    }
}
