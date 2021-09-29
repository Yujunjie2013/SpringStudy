package com.test.rabbit.controller;

import com.test.rabbit.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Slf4j
@RestController
public class XDelayedController {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //发送一条基于插件的延时消息
    @GetMapping("/xdelay/{msg}/{time}")
    public void sendMsg(@PathVariable("msg") String msg, @PathVariable("time") Integer time) {
        log.info("发送一条时长{}毫秒信息给延迟队列delayed.queue:{}", time, msg);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY,
                msg, message -> {
                    message.getMessageProperties().setDelay(time);
                    return message;
                });

    }
}
