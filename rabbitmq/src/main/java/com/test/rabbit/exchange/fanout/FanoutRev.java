package com.test.rabbit.exchange.fanout;

import com.rabbitmq.client.*;

public class FanoutRev {
    private static final String EXCHANGE_NAME = "fanout_exchange";
    private static final String QUEUE_NAME = "fanout_queue_1";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.54.206.104");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明交换机,指定类型为fanout
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
//        String queueName = channel.queueDeclare().getQueue();

        //消费者声明自己的队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //绑定交换机和队列
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            System.out.println("-----：" + consumerTag);
        });
    }
}
