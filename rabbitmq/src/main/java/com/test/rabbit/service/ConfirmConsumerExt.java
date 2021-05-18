package com.test.rabbit.service;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
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
 * rabbitmq:
 * listener:
 * simple:
 * acknowledge-mode: manual
 * 或在 RabbitListenerContainerFactory 中进行开启手动 ack
 *
 * @Bean public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory){
 * SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
 * factory.setConnectionFactory(connectionFactory);
 * factory.setMessageConverter(new Jackson2JsonMessageConverter());
 * factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);             //开启手动 ack
 * return factory;
 * }
 * <p>
 * 多个消费者可以订阅同一个队列，这时队列中的消息会被平均分摊（Round-Robin即轮询)多个消费者进行处理，而不是每个消费者都收到所有的消息并处理
 * RabbitMQ不支持队列层面的广播消费，如果需要广播消费，需要在其上进行二次开发，处理逻辑会变得异常复杂，同时也不建议这么做。
 */
@Component
@Slf4j
public class ConfirmConsumerExt {
    private HashMap<String, Integer> hashMap = new HashMap<>();
    @Value("${server.port}")
    private int port;

    /**
     * 根据结果可知，当Exchange和RoutingKey相同、queue不同时，所有消费者都能消费同样的信息；
     * <p>
     * 当Exchange和RoutingKey、queue都相同时（最后两个消费者），消费者中只有一个能消费信息，其他消费者都不能消费该信息。
     *
     * @param message
     * @param headers
     * @param channel
     * @throws Exception
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "colorQueue", durable = "true"),
                    exchange = @Exchange(value = "testExchange", type = ExchangeTypes.DIRECT),
                    key = {"red"}
            )
    )
    @RabbitHandler
    public void receive(Message message, @Headers Map<String, Object> headers, Channel channel) throws Exception {
        //手动确认则当消费者调用 ack、nack、reject 几种方法进行确认
        // 唯一的消息ID
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("端口:{},isOpen:{},收到消息:{};消息唯一deliverTag:{},消息ID{}", port, channel.isOpen(), str, deliverTag, messageId);
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
                    //如果超过5次那么直接成功,把消息记录到数据库
                    log.error("直接成功了消息id:" + messageId + "消息内容:" + str);
                    channel.basicAck(deliverTag, false);//只有确认了后面的消息才会继续消费后面的
                }
            }
        }

    }


    /**
     * 死信队列
     *
     * @param message
     * @param headers
     * @param channel
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "redirectQueue", durable = "true"),
                    exchange = @Exchange(value = "dxlExchange", type = ExchangeTypes.DIRECT),
                    key = {"KEY_R"}
            )
    )
    @RabbitHandler
    public void receiveDLX(Message message, @Headers Map<String, Object> headers, Channel channel) {
        //手动确认则当消费者调用 ack、nack、reject 几种方法进行确认
        // 唯一的消息ID
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("端口:{} isOpen:{}-死信收到消息:{};消息deliverTag:{},消息ID{}", port, channel.isOpen(), str, deliverTag, messageId);
        try {
            if (str.contains("a")) {
                Thread.sleep(100000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                channel.basicAck(deliverTag, false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /*
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
                    //如果超过5次那么直接成功,把消息记录到数据库
                    log.error("直接成功了消息id:" + messageId + "消息内容:" + str);
                    channel.basicAck(deliverTag, false);//只有确认了后面的消息才会继续消费后面的
                }
            }
        }*/

    }


    /**
     * fanout模式，启动2个不同端口的服务，对应下面2个不同的QueueBinding，向testFanoutExchange交换机发送消息后，2个服务都可以收到消息
     *
     * @param message
     * @param headers
     * @param channel
     */
    @RabbitListener(
            bindings = {
                    @QueueBinding(
                            value = @Queue(value = "fanoutQueue1", durable = "true"),
                            exchange = @Exchange(value = "testFanoutExchange", type = ExchangeTypes.FANOUT),
                            key = {"fanout"})
                    //,
//                    @QueueBinding(value = @Queue(value = "fanoutQueue2", durable = "true"),
//                            exchange = @Exchange(value = "testFanoutExchange", type = ExchangeTypes.FANOUT),
//                            key = {"fanout"})
            }
    )
    @RabbitHandler
    public void receivefan(Message message, @Headers Map<String, Object> headers, Channel channel) {
        //手动确认则当消费者调用 ack、nack、reject 几种方法进行确认
        // 唯一的消息ID
        log.info("线程:" + Thread.currentThread().getName());
        Long deliverTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        MessageProperties messageProperties = message.getMessageProperties();
        String messageId = messageProperties.getMessageId();
        String str = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("端口:{} 是否打开{},fanout收到消息:{};消息deliverTag:{},消息ID{}", port, channel.isOpen(), str, deliverTag, messageId);
        try {
            if (deliverTag > 50) {
                log.info("此消息没法确认:{}", deliverTag);
            } else {
                channel.basicAck(deliverTag, false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
