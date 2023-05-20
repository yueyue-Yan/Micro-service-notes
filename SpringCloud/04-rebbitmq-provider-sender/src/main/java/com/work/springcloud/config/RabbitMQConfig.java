package com.work.springcloud.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




/**
 * ClassName:RabbitMQConfig
 * Package:com.work.springcloud.config
 * Description: 描述信息
 *
 * @date:2023/5/19 20:44
 * @author:yueyue
 */

@Configuration
public class RabbitMQConfig {

    //-------声明基本的消息队列-------
    @Bean
    public Queue baseQueue(){
        /*
            public Queue(String name)
            默认： 持久化、非排外、非自动删除
            public Queue(String name, boolean durable)
            public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete)
            public Queue(String name, boolean durable, boolean exclusive, boolean autoDelete,@Nullable Map<String, Object> arguments)
            参数1，消息队列的名称
            参数2，是否持久化，true代表持久化消息到消息队列中，false代表非持久化
            参数3，是否排外，false代表当前消息只能被一个消费者进行消费，true代表当前消息可以被多个消费者消费
            参数4，是否自动删除，是否自动删除消息队列，当没有消费者监听这个队列时，这个队列就会被自动删除掉
            参数5，消息队列的其他参数，指定过期时间等等，可以在管理控制台进行查看
         */
        return new Queue("baseQueue");
    }
    //-------声明基本的消息队列-------





    //-------声明Direct类型交换机、消息队列、绑定关系-------
    @Bean
    public Exchange directExchange(){
        /*
            public DirectExchange(String name)
                持久化、非自动删除的交换机
            public DirectExchange(String name, boolean durable, boolean autoDelete)
            public DirectExchange(String name, boolean durable, boolean autoDelete, Map<String, Object> arguments)
            参数1，交换机名称
            参数2，是否持久化
            参数3，是否自动删除
            参数4，交换机的其他参数，管理控制台可以查看
         */
        return new DirectExchange("directExchange");
    }



    @Bean
    public Queue directQueue(){
        return new Queue("directQueue");
    }

    @Bean
    public Binding directBinding(Exchange directExchange,Queue directQueue){
        return BindingBuilder.bind(directQueue).to(directExchange).with("directRoutingKey").noargs();
    }

    //-------声明Direct类型交换机、消息队列、绑定关系-------






    //-------声明Fanout类型交换机、消息队列、绑定关系-------
    @Bean
    public Exchange fanoutExchange(){
        //持久化，非自动删除的交换机类型
        return new FanoutExchange("fanoutExchange");
    }

    @Bean
    public Queue fanoutQueue1(){
        return new Queue("fanoutQueue1",false,true,true);
    }

    @Bean
    public Queue fanoutQueue2(){
        return new Queue("fanoutQueue2",false,true,true);
    }

    @Bean
    public Queue fanoutQueue3(){
        return new Queue("fanoutQueue3",false,true,true);
    }

    @Bean
    public Binding fanoutBinding1(Queue fanoutQueue1,Exchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue1).to(fanoutExchange).with("").noargs();
    }

    @Bean
    public Binding fanoutBinding2(Queue fanoutQueue2,Exchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue2).to(fanoutExchange).with("").noargs();
    }

    @Bean
    public Binding fanoutBinding3(Queue fanoutQueue3,Exchange fanoutExchange){
        return BindingBuilder.bind(fanoutQueue3).to(fanoutExchange).with("").noargs();
    }

    //-------声明Fanout类型交换机、消息队列、绑定关系-------






    //-------声明Topic类型交换机、消息队列、绑定关系-------

    @Bean
    public Exchange topicExchange(){
        //持久化、非自动删除的交换机
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue("topicQueue1");
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue("topicQueue2");
    }

    @Bean
    public Queue topicQueue3(){
        return new Queue("topicQueue3");
    }

    @Bean
    public Binding topicBinding1(Exchange topicExchange,Queue topicQueue1){
        return BindingBuilder.bind(topicQueue1).to(topicExchange).with("aa").noargs();
    }

    @Bean
    public Binding topicBinding2(Exchange topicExchange,Queue topicQueue2){
        return BindingBuilder.bind(topicQueue2).to(topicExchange).with("aa.*").noargs();
    }

    @Bean
    public Binding topicBinding3(Exchange topicExchange,Queue topicQueue3){
        return BindingBuilder.bind(topicQueue3).to(topicExchange).with("aa.#").noargs();
    }

    //-------声明Topic类型交换机、消息队列、绑定关系-------






}
