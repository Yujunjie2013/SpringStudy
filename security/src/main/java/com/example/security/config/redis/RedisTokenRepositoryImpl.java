package com.example.security.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Component;
import redis.clients.jedis.*;

import java.util.*;

@Component
@Slf4j
public class RedisTokenRepositoryImpl implements PersistentTokenRepository {

    @Autowired
    private JedisCluster jedisCluster;

    private static final Integer TOKEN_VALID_SECOND = 3600;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        if (log.isDebugEnabled()) {
            log.debug("token create seriesId: [{}]", token.getSeries());
        }
        log.info("createNewToken-->" + token);
        String key = generateKey(token.getSeries());
        HashMap<String, String> map = new HashMap<>();
        map.put("username", token.getUsername());
        map.put("tokenValue", token.getTokenValue());
        map.put("date", String.valueOf(token.getDate().getTime()));
        jedisCluster.hmset(key, map);
        jedisCluster.expire(key, TOKEN_VALID_SECOND);
    }

    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        String key = generateKey(series);
        HashMap<String, String> map = new HashMap<>();
        map.put("tokenValue", tokenValue);
        map.put("date", String.valueOf(lastUsed.getTime()));
        jedisCluster.hmset(key, map);
        jedisCluster.expire(key, TOKEN_VALID_SECOND);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        String key = generateKey(seriesId);

        Map<String, String> hashValues = jedisCluster.hgetAll(key);
        String username = hashValues.get("username");
        String tokenValue = hashValues.get("tokenValue");
        String date = hashValues.get("date");

        if (null == username || null == tokenValue || null == date) {
            return null;
        }
        Long timestamp = Long.valueOf(date);
        Date time = new Date(timestamp);
        return new PersistentRememberMeToken(username, seriesId, tokenValue, time);
    }

    @Override
    public void removeUserTokens(String username) {
        if (log.isDebugEnabled()) {
            log.debug("token remove username: [{}]", username);
        }
        Set<String> strings = scanKey(generateKey("*"));
        for (String string : strings) {
            Map<String, String> stringStringMap = jedisCluster.hgetAll(string);
            if (stringStringMap != null && stringStringMap.size() != 0) {
                String name = stringStringMap.get("username");
                System.out.println("name:" + name);
                if (username.equals(name)) {
                    System.out.println("开始删除---->" + name);
                    jedisCluster.del(username);
                    break;
                }

            }
        }
    }

    // 遍历redis所有key
    public Set<String> scanKey(String pattern) {
        HashSet<String> allList = new HashSet<>();
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        for (String key : clusterNodes.keySet()) {
            JedisPool jedisPool = clusterNodes.get(key);
            Jedis resource = null;
            try {
                resource = jedisPool.getResource();
                String scanCursor = "0";
                List<String> scanResult;
                ScanParams scanParams = new ScanParams();
                scanParams.count(1000);
                scanParams.match(pattern);
                do {
                    ScanResult<String> scan = resource.scan(scanCursor, scanParams);
                    scanCursor = scan.getCursor();
                    scanResult = scan.getResult();
                    allList.addAll(scanResult);
                } while (!scanCursor.equals("0") && !scanResult.isEmpty());
            } catch (Exception e) {
                System.out.println("Getting keys error: " + e);
            } finally {
                if (resource != null) {
                    resource.close();
                }
            }
        }
        return allList;
    }

    /**
     * 生成key
     *
     * @param series
     * @return
     */
    private String generateKey(String series) {
        return "spring:security:rememberMe:token:" + series;
    }
}
