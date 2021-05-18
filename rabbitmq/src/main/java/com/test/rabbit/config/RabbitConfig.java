package com.test.rabbit.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class RabbitConfig {

    /*----------------------以下代码演示direct交换机-----------------------*/
    @Bean
    public Queue colorQueue() {
        //持久化队列,以下两种方式等效
        return QueueBuilder.durable("colorQueue").build();
//        return new Queue("red", true, false, false);
    }

    @Bean
    public Exchange testExchange() {
        return ExchangeBuilder.directExchange("testExchange").durable(true).build();
    }

    @Bean
    public Binding colorBinding() {
        return new Binding("colorQueue", Binding.DestinationType.QUEUE, "testExchange", "red", null);
    }
    /*----------------------以上代码演示direct交换机-----------------------*/

    /*----------------------以下代码演示fanout交换机-----------------------*/
    @Bean
    public Exchange testFanoutExchange() {
        return ExchangeBuilder.fanoutExchange("testFanoutExchange").durable(true).build();
    }

    @Bean
    public Queue fanoutQueue1() {
        //持久化队列,以下两种方式等效
        return QueueBuilder.durable("fanoutQueue1").build();
    }

    @Bean
    public Queue fanoutQueue2() {
        //持久化队列,以下两种方式等效
        return QueueBuilder.durable("fanoutQueue2").build();
//        return new Queue("red", true, false, false);
    }

    @Bean
    public Binding fanoutBinding1() {
        return new Binding("fanoutQueue1", Binding.DestinationType.QUEUE, "testFanoutExchange", "fanout", null);
    }

    @Bean
    public Binding fanoutBinding2() {
        return new Binding("fanoutQueue2", Binding.DestinationType.QUEUE, "testFanoutExchange", "fanout", null);
    }
    /*----------------------以上代码演示fanout交换机-----------------------*/


    /******************以下代码是演示死信队列*******************/
    @Bean
    public Exchange dxlExchange() {
        //用于死信队列的交换机
        return ExchangeBuilder.directExchange("dxlExchange").durable(true).build();
    }

    @Bean
    public Queue deadLetterQueue() {
        Map<String, Object> args = new HashMap<>(2);
//       x-dead-letter-exchange    声明  死信交换机
        args.put("x-dead-letter-exchange", "dxlExchange");
//       x-dead-letter-routing-key    声明 死信路由键
        args.put("x-dead-letter-routing-key", "KEY_R");
        return QueueBuilder.durable("deadLetterQueue").withArguments(args).build();
    }

    @Bean
    public Queue redirectQueue() {
        //声明了一个替补队列redirectQueue，变成死信的消息最终就是存放在这个队列的。
        return QueueBuilder.durable("redirectQueue").build();
    }

    /**
     * 死信路由通过 DL_KEY 绑定键绑定到死信队列上.
     *发送消息的时候朝这个 路由键上发送，由于消息设置了时效性，到期后会发送到redirectQueue队列中，所以只需要监听该队列即可
     * @return the binding
     */
    @Bean
    public Binding deadLetterBinding() {
        return new Binding("deadLetterQueue", Binding.DestinationType.QUEUE, "dxlExchange", "DL_KEY", null);

    }

    /**
     * 死信路由通过 KEY_R 绑定键绑定到死信队列上.
     *
     * @return the binding
     */
    @Bean
    public Binding redirectBinding() {
        return new Binding("redirectQueue", Binding.DestinationType.QUEUE, "dxlExchange", "KEY_R", null);
    }


}
