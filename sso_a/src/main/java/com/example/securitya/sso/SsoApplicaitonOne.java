package com.example.securitya.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Sso
public class SsoApplicaitonOne {
    public static void main(String[] args) {
        SpringApplication.run(SsoApplicaitonOne.class, args);
    }
}
