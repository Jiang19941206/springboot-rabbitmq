package com.jiangcl.springbootrabbitmq.config.work;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc springboot work模式
 *  当在一个类中需要定义多个消费者时，可以将 @RabbitListener 注解表明在类上面
 *  负载均衡模式，就是多个消费者消费同一个队列中的消息，一条消息只会被多个消费者中的某一个消费
 */
@Component
public class WorkConsumer {

    //设置消费者的确认机制，并达到能者多劳的效果
    @Bean("workListenerFactory")
    public RabbitListenerContainerFactory myFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory containerFactory =
                new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        //自动ack,没有异常的情况下自动发送ack
        //auto  自动确认,默认是auto
        //MANUAL  手动确认
        //none  不确认，发完自动丢弃
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //拒绝策略,true回到队列 false丢弃，默认是true
        containerFactory.setDefaultRequeueRejected(true);

        //默认的PrefetchCount是250，采用Round-robin dispatching，效率低
        //setPrefetchCount 为 1，即可启用fair 转发
        containerFactory.setPrefetchCount(1);
        return containerFactory;
    }


    @RabbitListener(queuesToDeclare = @Queue(value = "boot-work"),containerFactory = "workListenerFactory")
    public void consumer1(String message){
        System.out.println("consumer1--->" + message);

        try {
            Thread.sleep(1000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queuesToDeclare = @Queue(value = "boot-work"),containerFactory = "workListenerFactory")
    public void consumer2(String message){
        System.out.println("consumer2--->" + message);
    }
}
