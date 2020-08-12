package com.test.rabbit.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 手动确认则当消费者调用 ack、nack、reject 几种方法进行确认
 * AcknowledgeMode.NONE：自动确认
 * AcknowledgeMode.AUTO：根据情况确认
 * AcknowledgeMode.MANUAL：手动确认
 * 默认是自动确认，如果需要手动确认，需要修改配置
 * spring:
 *   rabbitmq:
 *     listener:
 *       simple:
 *         acknowledge-mode: manual
 *或在 RabbitListenerContainerFactory 中进行开启手动 ack
 *@Bean
 * public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
 *     SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
 *     factory.setConnectionFactory(connectionFactory);
 *     factory.setMessageConverter(new Jackson2JsonMessageConverter());
 *     factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);             //开启手动 ack
 *     return factory;
 * }
 *
 */
@Component
@Slf4j
public class ConfirmConsumer {
    private HashMap<String, Integer> hashMap = new HashMap<>();

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "top_queue_confirm", autoDelete = "false", exclusive = "false", durable = "true", declare = "false"),
                    exchange = @Exchange(value = "top_confirm", type = ExchangeTypes.TOPIC),
                    key = {"red.#"}
            )
    )
    @RabbitHandler
    public void receive(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        //手动确认则当消费者调用 ack、nack、reject 几种方法进行确认
        // 唯一的消息ID
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        log.info("收到消息-------:{};消息唯一deliverTag:{},消息ID{}", message, deliverTag, messageId);
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        Integer integer = hashMap.get(messageId);
        if (integer == null) {
            // 确认该条消息
            if (str.contains("我来了")) {
                //multiple参数：为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
                //如果某个服务忘记 ACK 了，则 RabbitMQ 不会再发送数据给它，因为 RabbitMQ 认为该服务的处理能力有限
                channel.basicAck(deliverTag, false);//只有确认了后面的消息才会继续消费后面的
            } else {
//            // 消费失败，消息重返队列，此时会重新发送过来消费,也可以拒绝
//            channel.basicReject((Long)map.get(AmqpHeaders.DELIVERY_TAG),false);        //拒绝消息
                channel.basicNack(deliverTag, false, true);
                hashMap.put(messageId, 1);
            }
        } else {
            // 确认该条消息
            if (str.contains("我来了")) {
                channel.basicAck(deliverTag, false);//只有确认了后面的消息才会继续消费后面的
            } else {
                if (integer < 5) {
//            // 消费失败，消息重返队列
                    channel.basicNack(deliverTag, false, true);
                    integer++;
                    hashMap.put(messageId, integer);
                } else {
                    //如果超过5次那么直接成功
                    log.error("直接成功了消息id:" + messageId);
                    channel.basicAck(deliverTag, false);//只有确认了后面的消息才会继续消费后面的
                }
            }
        }

    }
}
