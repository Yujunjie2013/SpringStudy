package com.test.rabbit.service;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 很少有人这么干，因为这是同步操作，一条消息发送之后会使发送端阻塞，以等待RabbitMQ-Server的回应，
 * 之后才能继续发送下一条消息，生产者生产消息的吞吐量和性能都会大大降低。
 */
//@Service
public class ProviderTranscation implements RabbitTemplate.ReturnCallback {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        // 设置channel开启事务
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setReturnCallback(this);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("这条消息发送失败了" + message + ",请处理");
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "rabbitTransactionManager")
    public void publishMessage(String message) throws Exception {
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.convertAndSend("javatrip", message);
    }
}
