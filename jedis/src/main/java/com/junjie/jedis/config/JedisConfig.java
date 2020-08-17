package com.junjie.jedis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class JedisConfig {

    @Bean
    public Jedis jedis() {
        Jedis jedis = new Jedis("localhost", 6379);
        return jedis;
    }
}
