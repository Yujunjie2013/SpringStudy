package com.example.security.config;

import com.example.security.bean.ClusterConfigurationProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TokenStoreConfig {
    @Autowired
    private ClusterConfigurationProperties clusterProperties;
    @Autowired
    private LettuceConnectionFactory lettuceConnectionFactory;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        //方式1
////        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
////        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8001));
////        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8002));
////        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8003));
////        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8004));
////        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8005));
////        redisClusterConfiguration.addClusterNode(new RedisNode("106.54.206.104", 8006));
//        //方式2
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
//        return new LettuceConnectionFactory(redisClusterConfiguration);
//    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate() {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        //使用Jackson2JsonRedisSerializer替换默认的序列化规则
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        //反序列化遇到未知字段不处理，如果不加这句，在反序列化如果没有该字段容易报错
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        //设置value的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        //设置key的序列化规则
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(lettuceConnectionFactory);
//        new BaseRedisTokenStoreSerializationStrategy();
//        redisTokenStore.setSerializationStrategy();
        return redisTokenStore;
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey("test_key"); // 签名密钥
        return accessTokenConverter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer() {
        return new JWTokenEnhancer();
    }

    //    如果想在JWT中添加一些额外的信息，我们需要实现TokenEnhancer（Token增强器）：
    public static class JWTokenEnhancer implements TokenEnhancer {
        @Override
        public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
            Map<String, Object> info = new HashMap<>();
            info.put("message", "hello world");
            ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(info);
            return oAuth2AccessToken;
        }
    }
}
