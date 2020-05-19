package com.example.security.config;

import com.example.security.bean.ClusterConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class TokenStoreConfig {
    @Autowired
    private ClusterConfigurationProperties clusterProperties;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        //方式1
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
//        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8001));
//        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8002));
//        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8003));
//        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8004));
//        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8005));
//        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8006));
        //方式2
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
        return new JedisConnectionFactory(redisClusterConfiguration);
    }

    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory());
    }
}
