package com.work.springcloud;

import com.work.springcloud.send.SendMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RabbitMQ7003Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(RabbitMQ7003Application.class, args);

        SendMessage bean = run.getBean(SendMessage.class);

        //发送最基本的消息到消息队列中
        bean.sendBaseMessage();

        //发送消息到Direct交换机中
        bean.sendDirectMessage();

        //发送消息到Fanout交换机中
        bean.sendFanoutMessage();

        //发送消息到Topic交换机中
        bean.sendTopicMessage();
    }

}
