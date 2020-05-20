package com.example.securityb.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
//@EnableOAuth2Client
@EnableOAuth2Sso
public class SSoApplicationTwo {
    public static void main(String[] args) {
        SpringApplication.run(SSoApplicationTwo.class,args);
    }
}
