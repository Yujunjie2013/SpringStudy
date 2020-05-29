package com.test.rabbit.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Fanout类型会给绑定的所有队列发送消息，无视路由键
 * <a>https://www.rabbitmq.com/tutorials/tutorial-three-java.html</a>
 */
public class FanoutSend {
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.54.206.104");
        factory.setPort(5672);
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //声明交换机,指定类型为fanout
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String message = args.length < 1 ? "info: Hello World!" :
                    String.join(" ", args);
            //因为exange使用的是FANOUT,所以会将消息路由给绑定到它身上的所有队列，而不理会绑定的路由键。故这里的routingKey没有设置
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
