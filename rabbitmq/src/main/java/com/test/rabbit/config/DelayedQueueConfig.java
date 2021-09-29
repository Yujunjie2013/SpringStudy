package com.test.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class DelayedQueueConfig {
    //队列
    public static final String DELAYED_QUEUE_NAME = "delayed.queue";
    //交换机
    public static final String DELAYED_EXCHANGE_NAME = "delayed.exchange";
    //routingkey
    public static final String DELAYED_ROUTING_KEY = "delayed.routingkey";
    //自定义类型，这个类型是我们在rabbitmq上安装的一个延时队列插件
    public static final String TYPE = "x-delayed-message";
    //key
    public static final String DELAYED_EXCHANGE_TYPE = "x-delayed-type";
    //key,指定延时时间
    public static final String X_DELAY = "x-delay";


    //声明交换机
    @Bean
    public CustomExchange delayedExchange() {
        HashMap<String, Object> arguments = new HashMap<>();
//        这个插件允许通过x-delayed-type参数进行灵活的路由，这些参数可以在exchange.declare期间传递。
//        在上面的示例中，我们使用“direct”作为交换类型。这意味着插件将具有与直接交换所显示的相同的路由行为。
//        如果您想要不同的路由行为，那么您可以提供不同的交换类型，例如“topic”。您还可以指定插件提供的交换类型。
//        注意，此参数是必需的，并且必须引用现有的交换类型。
        arguments.put(DELAYED_EXCHANGE_TYPE, ExchangeTypes.DIRECT);
        return new CustomExchange(DELAYED_EXCHANGE_NAME, TYPE, true, false, arguments);
    }

    //创建队列
    @Bean
    public Queue delayedQueue() {
        return new Queue(DELAYED_QUEUE_NAME, true, false, false);
    }

    //绑定
    @Bean
    public Binding delayedQueueBindingDelayedExchange(Queue delayedQueue, Exchange delayedExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}
