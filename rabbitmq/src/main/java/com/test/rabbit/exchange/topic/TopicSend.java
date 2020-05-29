package com.test.rabbit.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TopicSend {
    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("106.54.206.104");
        factory.setPort(5672);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // 声明exchange，指定类型为direct
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String message = "新增一个订单";
        //生产者发送消息时，设置消息的Routing Key:"black"
//        channel.basicPublish(EXCHANGE_NAME, "black", null, message.getBytes());
        //生产者发送消息时，设置消息的Routing Key:"red"
        channel.basicPublish(EXCHANGE_NAME, "red.yellow.green", null, message.getBytes());
        //生产者发送消息时，设置消息的Routing Key:"green"
//        channel.basicPublish(EXCHANGE_NAME, "green", null, message.getBytes());
        //生产者发送消息时，设置消息的Routing Key:"blue"
//        channel.basicPublish(EXCHANGE_NAME, "blue", null, message.getBytes());
        System.out.println("生产者发送消息：" + message);
        channel.close();
        connection.close();
    }
}
