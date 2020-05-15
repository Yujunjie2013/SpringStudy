package com.junjie.test;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

public class JedisUtils {

    static {
        Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
        //Jedis Cluster will attempt to discover cluster nodes automatically
        jedisClusterNodes.add(new HostAndPort("106.54.206.104", 8001));
        jedisClusterNodes.add(new HostAndPort("106.54.206.104", 8002));
        jedisClusterNodes.add(new HostAndPort("106.54.206.104", 8003));
        jedisClusterNodes.add(new HostAndPort("106.54.206.104", 8004));
        jedisClusterNodes.add(new HostAndPort("106.54.206.104", 8005));
        jedisClusterNodes.add(new HostAndPort("106.54.206.104", 8006));

        JedisPoolConfig config = new JedisPoolConfig();

        config.setMaxTotal(500);

        config.setMaxIdle(100);
        config.setMaxIdle(2000);
//        config.setTestOnBorrow(true);

        //connectionTimeout：指的是连接一个url的连接等待时间
        //soTimeout：指的是连接上一个url，获取response的返回等待时间
        jc = new JedisCluster(jedisClusterNodes, config);
    }

    private static JedisCluster jc;

    public static JedisCluster getJc() {
        return jc;
    }
}
