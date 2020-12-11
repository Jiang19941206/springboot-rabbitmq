package com.jiangcl.springbootrabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author jiangcl
 * @date 2020/12/10
 * @desc
 */
@SpringBootTest(classes = SpringbootRabbitmqApplication.class)
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testDemo(){
        rabbitTemplate.convertAndSend("boot-demo","hello boot rabbit demo");
    }

    @Test
    public void testWork(){
        int counter = 1;
        while (true){
            rabbitTemplate.convertAndSend("boot-work","work消息" + counter);
            counter++;
            try {
                Thread.sleep(500l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void testFanout(){
        rabbitTemplate.convertAndSend("boot-fanout","","使用 fanout 模型发送的消息");
    }

    @Test
    public void testRoute(){
        rabbitTemplate.convertAndSend("boot-route-direct","info","info消息");
        rabbitTemplate.convertAndSend("boot-route-direct","error","error消息");
        rabbitTemplate.convertAndSend("boot-route-direct","warning","warning消息");
    }

    @Test
    public void testRouteTopic(){
        rabbitTemplate.convertAndSend("boot-route-topic","user.name","user.name消息");
        rabbitTemplate.convertAndSend("boot-route-topic","order.address","order.address消息");
        rabbitTemplate.convertAndSend("boot-route-topic","product.name","product.name消息");
        rabbitTemplate.convertAndSend("boot-route-topic","order.address.city","order#消息");
    }
}
