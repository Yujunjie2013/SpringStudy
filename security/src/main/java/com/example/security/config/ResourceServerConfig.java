package com.example.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@Configuration
@EnableResourceServer//资源服务器,认证服务器和资源服务器可以是同一个服务器，
@Order(3)
/**
 * 在同时定义了认证服务器和资源服务器后，再去使用授权码模式获取令牌可能会遇到
 * Full authentication is required to access this resource 的问题，这时候只要确保认证服务器先于资源服务器配置即可，
 * 比如在在BrowserSecurityConfig上使用@Order(1)，认证服务器的配置类上使用@Order(2)标注，在资源服务器的配置类上使用@Order(3)标注。
 */
public class ResourceServerConfig {
}
