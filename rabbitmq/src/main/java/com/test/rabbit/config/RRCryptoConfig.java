package com.test.rabbit.config;

import cn.hutool.crypto.symmetric.AES;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RRCryptoConfig {
    /**
     * 加密解密方式使用一样的
     */
    @Bean()
    public AES rrCrypto(){
        return new AES("abcdef00poiuytre".getBytes());
    }


}
