package com.junjie.test.config;

import com.junjie.test.bean.User;
import org.springframework.context.annotation.Bean;

public class UserConfiguration {
    @Bean
    public User user() {
        return new User("张三", 12);
    }
}
