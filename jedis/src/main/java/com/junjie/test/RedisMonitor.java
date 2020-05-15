package com.junjie.test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisMonitor;

/**
 * Redis 命令监控
 */
public class RedisMonitor {
    static class MonitorTask implements Runnable{

        private Jedis jedis;

        public MonitorTask(Jedis jedis) {
            this.jedis = jedis;
        }

        public void run() {
            jedis.monitor(new JedisMonitor() {
                @Override
                public void onCommand(String command) {
                    System.out.println(command);
                }
            });
        }
    }

    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        Thread thread = new Thread(new MonitorTask(jedis));
        thread.start();
    }
}
