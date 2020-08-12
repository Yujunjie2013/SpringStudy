package com.test.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class RabbitConfig {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private RabbitTemplate rabbitTemplate;

//    @Bean
//    public Queue helloQueue() {
//
////        System.out.println("创建队列");
//        log.info("创建队列");
////        return new Queue("topic_queue_3", false, false, false);
//        //持久化队列
//        return new Queue("top_queue_confirm", true, false, false);
//    }

//    @Bean
//    public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
//        return new RabbitTransactionManager(connectionFactory);
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        return new RabbitTemplate(connectionFactory);
//    }
}
