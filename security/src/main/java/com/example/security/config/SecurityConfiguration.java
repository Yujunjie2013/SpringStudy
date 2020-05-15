package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//禁用认证
//@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
//        当Spring项目中引入了Spring Security依赖的时候，项目会默认开启如下配置：
//        security:basic:enabled: true
//        通过下面的代码禁用
        http.httpBasic().disable();
    }
}
