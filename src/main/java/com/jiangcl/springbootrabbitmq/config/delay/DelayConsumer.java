package com.jiangcl.springbootrabbitmq.config.delay;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc 延迟消息消费
 */
@Component
public class DelayConsumer {

    @RabbitListener(queues = "process.queue")
    public void delayConsumer(String message){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " 消费 "+ message);
    }
}
