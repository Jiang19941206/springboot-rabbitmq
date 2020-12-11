package com.jiangcl.springbootrabbitmq.config.delay;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc 使用死信队列实现延迟消息
 *      生产者把带有 ttl(Time-To-Live过期时间) 的消息发送到一个临时队列(DelayQueue)，该队列没有消费者；
 *   该消息在DelayQueue中停留直至过期，同时该消息没有ReQueue(重新入队)，就变成了死信(Dead-letter或Dead-message)，死信自动地被发送给了配置好的DLX（Dead-Letter-Exchange）；
 *   DLX根据路由规则把消息路由到了配置好的队列中(DeadLetterQueue)，队列中的消息被消费者消费。
 */
@Configuration
public class DelayQueueConfig {
    /**为了更贴合业务，参数名不使用DeadQueue之类的*/
    /**延迟队列名*/
    private static String DELAY_QUEUE = "delay.queue";
    /**延迟队列(死信队列)交换器名*/
    private static String DELAY_EXCHANGE = "delay.exchange";
    /**处理业务的队列(死信队列)*/
    private static String PROCESS_QUEUE = "process.queue";
    /**ttl(10秒)*/
    private static int DELAY_EXPIRATION = 10000;

    /**
     * 创建延迟队列
     * "x-dead-letter-exchange"参数定义死信队列交换机
     * "x-dead-letter-routing-key"定义死信队列中的消息重定向时的routing-key
     * "x-message-ttl"定义消息的过期时间
     */
    @Bean
    public Queue delayQueue(){
        return QueueBuilder.durable(DELAY_QUEUE)
                .withArgument("x-dead-letter-exchange", DELAY_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", PROCESS_QUEUE)
                .withArgument("x-message-ttl", DELAY_EXPIRATION)
                .build();
    }

    /**创建用于业务的队列*/
    @Bean("processQueue")
    public Queue processQueue(){
        return QueueBuilder.durable(PROCESS_QUEUE)
                .build();
    }

    /**创建一个DirectExchange*/
    @Bean("directExchange")
    public DirectExchange delayExchange(){
        return new DirectExchange(DELAY_EXCHANGE);
    }

    /**绑定Exchange和queue，把消息重定向到业务queue*/
    @Bean
    Binding dlxBinding(DirectExchange directExchange, Queue processQueue){
        return BindingBuilder.bind(processQueue)
                .to(directExchange)
                .with(PROCESS_QUEUE);
        //绑定，以PROCESS_QUEUE为routing key
    }
}
