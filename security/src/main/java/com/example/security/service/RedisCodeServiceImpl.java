package com.example.security.service;

import com.example.security.bean.SmsCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.ServletWebRequest;
import redis.clients.jedis.JedisCluster;

/**
 * Redis操作验证码服务
 */
@Service
public class RedisCodeServiceImpl implements RedisCodeService {
    //短信验证码前缀
    private final static String SMS_CODE_PREFIX = "SMS_CODE:";
    //默认设备ID
    private final static String DEFAULT_DEVICEID = "DFID:";
    //超时时间
    private final static Integer TIME_OUT = 300;
    @Autowired
    private JedisCluster jedisCluster;

    @Override
    public void save(SmsCode smsCode, ServletWebRequest request, String mobile) {
        jedisCluster.setex(key(request, mobile), TIME_OUT, smsCode.getCode());
    }

    @Override
    public String get(ServletWebRequest request, String mobile) {
        return jedisCluster.get(key(request, mobile));
    }

    @Override
    public void remove(ServletWebRequest request, String mobile) {
        jedisCluster.del(key(request, mobile));
    }

    private String key(ServletWebRequest request, String mobile) {
        String deviceId = request.getHeader("deviceId");
        if (StringUtils.isBlank(deviceId)) {
            deviceId = DEFAULT_DEVICEID;
        }
        return SMS_CODE_PREFIX + deviceId + ":" + mobile;
    }
}
