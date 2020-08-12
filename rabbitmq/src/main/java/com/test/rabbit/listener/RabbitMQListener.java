package com.test.rabbit.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

//@Component
public class RabbitMQListener {
    /**
     * 这里的队列名称spring.test.queue，在RabbitApplication中有声明，否则启动会报错
     *
     * @param msg
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "topic_queue_2", autoDelete = "false", exclusive = "false", durable = "true", declare = "false"),
                    exchange = @Exchange(value = "topic_logs", type = ExchangeTypes.TOPIC),
                    key = {"red.#"}
            )
    )
    public void listen(String msg) {
        System.out.println("消费者接受到消息：" + msg);
    }

}
