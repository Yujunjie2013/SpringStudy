package com.test.rabbit.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * 1、通过实现 ConfirmCallback 接口，消息发送到 Broker 后触发回调，确认消息是否到达 Broker 服务器，
 * 也就是只确认是否正确到达 Exchange 中
 * 同时需要在配置文件中添加如下配置
 * spring:
 * rabbitmq:
 * publisher-confirms: true
 * 2、通过实现 ReturnCallback 接口，启动消息失败返回，比如路由不到队列时触发回调
 * 同时需要在配置文件中添加如下配置
 * spring:
 * rabbitmq:
 * publisher-returns: true
 */
@Service
@Slf4j
public class ConfirmProviderExt implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("确认了这条消息{},cause:{}", correlationData, cause);
        } else {
            log.info("确认失败了:{};出现异常:{}", correlationData, cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.info("这条消息发送失败了{},replyCode:{},replyTest:{},exchange:{},routingKey:{}", message, replyCode, replyText, exchange, routingKey);
    }

    public void publisMessage(String msg) {
        String Id = UUID.randomUUID().toString();
        rabbitTemplate.setMandatory(true);
        Message message = MessageBuilder.withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("UTF-8")
                .setMessageId(Id).build();
        //如果不添加CorrelationData 那么confirm方法中回调回来的对象会是null
        CorrelationData correlationData = new CorrelationData(Id);
        correlationData.setReturnedMessage(message);
        //Exchange和RoutingKey都相同，最后两个消费者队列名都相同。
        rabbitTemplate.convertAndSend("testExchange", "red", message, correlationData);
    }

    //向死信队列发送消息
    public void publisDlXMessage(String msg) {
        msg = "死信:" + msg;
        String Id = UUID.randomUUID().toString();
        rabbitTemplate.setMandatory(true);
        Message message = MessageBuilder.withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("UTF-8")
                .setExpiration("5000")//5*1000,消息的超时时间
                .setMessageId(Id).build();
        //如果不添加CorrelationData 那么confirm方法中回调回来的对象会是null
        CorrelationData correlationData = new CorrelationData(Id);
        correlationData.setReturnedMessage(message);
        //Exchange和RoutingKey都相同，最后两个消费者队列名都相同。
        rabbitTemplate.convertAndSend("dxlExchange", "DL_KEY", message, correlationData);
    }


    public void publisFanoutMessage(String msg) {
        log.info("发送线程:" + Thread.currentThread().getName());
        String Id = UUID.randomUUID().toString();
        rabbitTemplate.setMandatory(true);
        Message message = MessageBuilder.withBody(msg.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
                .setContentEncoding("UTF-8")
                .setMessageId(Id).build();
        //如果不添加CorrelationData 那么confirm方法中回调回来的对象会是null
        CorrelationData correlationData = new CorrelationData(Id);
        correlationData.setReturnedMessage(message);
        //Exchange和RoutingKey都相同，最后两个消费者队列名都相同。
        rabbitTemplate.convertAndSend("testFanoutExchange", "fanout", message, correlationData);
    }


}
