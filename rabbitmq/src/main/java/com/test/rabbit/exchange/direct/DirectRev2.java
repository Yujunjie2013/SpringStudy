package com.test.rabbit.exchange.direct;

import com.rabbitmq.client.*;

public class DirectRev2 {
    private static final String EXCHANGE_NAME = "direct_logs";
    private static final String QUEUE_NAME = "direct_queue_2";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.54.206.104");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //消费者声明自己的队列
        channel.queueDeclare(QUEUE_NAME, false, false, true, null);
        //绑定
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "red");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "black");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
        });
    }
}
