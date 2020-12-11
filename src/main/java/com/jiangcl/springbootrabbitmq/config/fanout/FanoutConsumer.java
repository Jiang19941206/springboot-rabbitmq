package com.jiangcl.springbootrabbitmq.config.fanout;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc springboot rabbitmq fanout模型
 *          广播模型：所有绑定该交换机的消费者都会消费到此交换机中的消息
 */
@Component
public class FanoutConsumer {

    //绑定队列与交换机
    @RabbitListener(bindings = {@QueueBinding(
                    value = @Queue,//创建临时队列
                    exchange = @Exchange(value = "boot-fanout",type = ExchangeTypes.FANOUT) //绑定交换机
            )})
    public void consumer1(String message){
        System.out.println("consumer1--->" + message);
    }


    @RabbitListener(bindings = {@QueueBinding(
            value = @Queue,//创建临时队列
            exchange = @Exchange(value = "boot-fanout",type = ExchangeTypes.FANOUT)//绑定交换机
    )})
    public void consumer2(String message){
        System.out.println("consumer2--->" + message);
    }
}
