package com.work.springcloud.send;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName:SendMessage
 * Package:com.work.springcloud.send
 * Description: 描述信息
 *
 * @date:2023/5/19 20:44
 * @author:yueyue
 */

@Slf4j
@Component
public class SendMessage {

    @Autowired
    private AmqpTemplate template;

    public void sendBaseMessage(){

        /*
            void convertAndSend(Object message)
            参数1，发送的消息实体
            void convertAndSend(String routingKey, Object message)
            参数1，路由键，根据指定的路由键，将消息从交换机路由到指定的消息队列中，如果没有指定交换机，则使用默认的交换机
                如果使用的是默认的交换机，则路由键命名为消息队列的名称
            参数2，发送的消息实体
            void convertAndSend(String exchange, String routingKey, Object message)
            参数1，交换机名称
            参数2，路由键，根据指定的路由键，将消息从交换机路由到指定的消息队列中
            参数3，发送的消息实体

            在发送消息之前，需要先声明，交换机、消息队列、绑定关系:com.work.springcloud.config.RabbitMQConfig
         */

        //发送一条消息，到默认的交换机中，将它路由到指定的消息队列中
        template.convertAndSend("baseQueue","测试...发送一条最基本的数据");

        log.info("发送成功...");
    }


    public void sendDirectMessage(){
        template.convertAndSend("directExchange","directRoutingKey","这是一条direct类型的消息 ╮(╯▽╰)╭ ");
    }

    public void sendFanoutMessage(){
        template.convertAndSend("fanoutExchange","","这是一条Fanout类型的消息 ╮(╯▽╰)╭ ");
    }

    public void sendTopicMessage(){
        template.convertAndSend("topicExchange","aa","这是一条topic类型的消息 ╮(╯▽╰)╭  aa");
        template.convertAndSend("topicExchange","aa.bb","这是一条topic类型的消息 ╮(╯▽╰)╭  aa.bb");
        template.convertAndSend("topicExchange","aa.bb.cc","这是一条topic类型的消息 ╮(╯▽╰)╭  aa.bb.cc");
    }

}
