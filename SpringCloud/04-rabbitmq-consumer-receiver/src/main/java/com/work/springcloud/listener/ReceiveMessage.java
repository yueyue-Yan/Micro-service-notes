package com.work.springcloud.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ClassName:ReceiveMessage
 * Package:com.work.springcloud.listener
 * Description: 描述信息
 *
 * @date:2023/5/19 20:54
 * @author:yueyue
 */

@Slf4j
@Component
public class ReceiveMessage {

    @RabbitListener(queues = {"baseQueue"})
    public void receiveBaseMessage(String msg){
        log.info("接收到 baseQueue 中的消息： {}",msg);
    }

    @RabbitListener(queues = {"directQueue"})
    public void receiveDirectMessage(String msg){
        log.info("接收到 directQueue 中的消息： {}",msg);
    }



    /*
        声明Fanout广播模式
     */
    @RabbitListener(
        //queues = {"fanoutQueue1"},
        //@Queue如果没有定义消息队列的名称，则使用的是随机名称的消息队列
        bindings = @QueueBinding(
                        value = @Queue ,
                        exchange = @Exchange(name = "fanoutExchange",type = "fanout"))
    )
    public void receiveFanoutMessage1(String msg){
        log.info("接收到fanout1类型消息：{}",msg);
    }

    @RabbitListener(
            //queues = {"fanoutQueue1"},
            //@Queue如果没有定义消息队列的名称，则使用的是随机名称的消息队列
            bindings = @QueueBinding(
                    value = @Queue ,
                    exchange = @Exchange(name = "fanoutExchange",type = "fanout"))
    )
    public void receiveFanoutMessage2(String msg){
        log.info("接收到fanout2类型消息：{}",msg);
    }

    @RabbitListener(
            //queues = {"fanoutQueue1"},
            //@Queue如果没有定义消息队列的名称，则使用的是随机名称的消息队列
            bindings = @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(name = "fanoutExchange",type = "fanout"))
    )
    public void receiveFanoutMessage3(String msg){
        log.info("接收到fanout3类型消息：{}",msg);
    }



    @RabbitListener(queues = {"topicQueue1"})
    public void receiveTopicQueue1Message(String msg){
        log.info("接收到 topicQueue1 的消息：{}",msg);
    }

    @RabbitListener(queues = {"topicQueue2"})
    public void receiveTopicQueue2Message(String msg){
        log.info("接收到 topicQueue2 的消息：{}",msg);
    }

    @RabbitListener(queues = {"topicQueue3"})
    public void receiveTopicQueue3Message(String msg){
        log.info("接收到 topicQueue3 的消息：{}",msg);
    }


}
