package com.example.security.service;

import com.example.security.bean.SmsCode;
import org.springframework.web.context.request.ServletWebRequest;

public interface RedisCodeService {
    /**
     * 保存验证码到redis
     *
     * @param smsCode
     * @param request
     * @param mobile
     */
    void save(SmsCode smsCode, ServletWebRequest request, String mobile) ;

    /**
     * 获取验证码
     *
     * @param request
     * @param mobile
     * @return
     */
    String get(ServletWebRequest request, String mobile);

    /**
     * 移除验证码
     *
     * @param request
     * @param mobile
     */
    void remove(ServletWebRequest request, String mobile) ;

}
