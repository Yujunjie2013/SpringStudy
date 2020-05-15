package com.test;

import com.junjie.test.JedisUtils;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

public class ApiTest {
    //    Jedis jedis;
    private static JedisCluster jedis;

    public final String KEY = "userName";
    public final String TEMP_KEY = "userName_TEMP";

    @Before
    public void init() {
//        jedis = new Jedis();//啥都不写，默认是localhost，和6379
        jedis = JedisUtils.getJc();
    }


    @Test
    public void testKey() {
        String set = jedis.set("userName", "李思思");
        String userName = jedis.get("userName");
//        Set<String> keys = jedisCluster.keys("*");
//        System.out.println("keys:"+keys.size());
        System.out.println("set:" + set + "----get:" + userName);
    }

    @Test
    public void testExpire() {
        String set = jedis.set("aaaaa", "双击评论666");
        System.out.println("---》" + set);
        Long expire = jedis.expire("aaaaa", 100);
        System.out.println("set：" + set + "--expire:" + expire);
    }

    @Test
    public void testttl() {
        //-1 表示永久有效,-2表示key不存在
        Long pttl = jedis.pttl(KEY);
        //毫秒
        Long pttl1 = jedis.pttl(TEMP_KEY);
        //秒
        Long ttl = jedis.ttl(TEMP_KEY);
        System.out.println(pttl + "-->" + pttl1 + "---ttl:" + ttl);
    }

    @Test
    public void testExists() {
        Boolean exists = jedis.exists(KEY);
        System.out.println(exists);
    }


    @Test
    public void testString() {
        //清空
//        jedis.flushDB();
        System.out.println("数据清空");
        testExists();
    }

    @Test
    public void testNxEx() throws InterruptedException {
        //key 不存在时创建，返回值为1
        Long 嘻嘻 = jedis.setnx(KEY, "嘻嘻");
        //key 已经存在则不创建，返回0
        Long 呵呵 = jedis.setnx(KEY, "呵呵");
        String s = jedis.get(KEY);
        System.out.println(嘻嘻 + "---" + 呵呵 + "======" + s);

        //设置值并设置有效期,成功返回OK
        String haha = jedis.setex(TEMP_KEY, 60, "哈哈");
        //上面异步相当于下面2步
//        jedis.set(TEMP_KEY,"哈哈");
//        jedis.expire(TEMP_KEY,60);


        Long ttl = jedis.ttl(TEMP_KEY);
        Thread.sleep(3000);
        Long ttl2 = jedis.ttl(TEMP_KEY);
        System.out.println("haha:" + haha + "---ttl:" + ttl + "---ttl2:" + ttl2);

    }

    @Test
    public void testIncr() {
        Long age = jedis.incr("age");
        Long score = jedis.incrBy("score", 2);
        System.out.println(age + "----" + score);
    }

    @Test
    public void hMap() {
        HashMap<String, String> stringStringHashMap = new HashMap<String, String>();
        stringStringHashMap.put("name", "zx");
        stringStringHashMap.put("age", "18");
        jedis.hmset("user.stua", stringStringHashMap);

        stringStringHashMap.put("name", "ls");
        stringStringHashMap.put("age", "28");
        jedis.hmset("user.stub", stringStringHashMap);
    }


    @Test
    public void keys2() {
        String pattern = "*";
        TreeSet<String> keys = new TreeSet<String>();
        Map<String, JedisPool> clusterNodes =
                jedis.getClusterNodes();
        for (String key : clusterNodes.keySet()) {
            System.out.println("Getting keys from:" + key);
            JedisPool jedisPool = clusterNodes.get(key);
            Jedis jedisConn = null;
            try {
                jedisConn = jedisPool.getResource();
                keys.addAll(jedisConn.keys(pattern));
            } catch (Exception e) {
                System.out.println("Getting keys error: " + e);
            } finally {
                System.out.println("Jedis connection closed");
                if (jedisConn != null) {
                    jedisConn.close();
                }
            }
        }
        System.out.println("keys:" + keys.size());
//        return keys;
    }

    LinkedBlockingQueue<List<Map.Entry<String, String>>> lbq = new LinkedBlockingQueue<List<Map.Entry<String, String>>>();
    //    LinkedBlockingQueue<List<Map.Entry<byte[], byte[]>>> lbq = new LinkedBlockingQueue<List<Map.Entry<byte[], byte[]>>>();
    Set<String> allList = new HashSet<String>();

    @Test
    public void keys() {
        scanSM();
//        Set<String> keys = jedis.keys("*");
        System.out.println(allList.size());
        for (String s : allList) {
            System.out.println(s);
        }
//        System.out.println(keys.size());
    }

    // 遍历redis所有key
    public void scanSM() {
        Map<String, JedisPool> clusterNodes = jedis.getClusterNodes();
        for (String key : clusterNodes.keySet()) {
            JedisPool jedisPool = clusterNodes.get(key);
            Jedis resource = null;
            try {
                resource = jedisPool.getResource();
                String scanCursor = "0";
                List<String> scanResult = new ArrayList<String>();
                ScanParams scanParams = new ScanParams();
                scanParams.count(1000);
                scanParams.match("user.stu*");
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
    }

    @Test
    public void testList() {
//        jedis.flushDB();
        System.out.println("---向集合中添加元素---");
        //返回的是添加成功的个数
        System.out.println(jedis.sadd("eleSet", "e1", "e2", "e4", "e3", "e0", "e8", "e7", "e5"));
        System.out.println(jedis.sadd("eleSet", "e6"));
        System.out.println(jedis.sadd("eleSet", "e6"));//这里重复添加返回的是0
        System.out.println("eleSet所有元素为:" + jedis.smembers("eleSet"));

        System.out.println("============================");
        System.out.println(jedis.sadd("eleSet1", "e1", "e2", "e4", "e3", "e0", "e8", "e7", "e5"));
        System.out.println("============集合运算=============");

        System.out.println("eleSet和eleSet1的交集：" + jedis.sinter("eleSet", "eleSet1"));
        System.out.println("eleSet和eleSet1的并集：" + jedis.sunion("eleSet", "eleSet1"));
        System.out.println("eleSet和eleSet1的差集：" + jedis.sdiff("eleSet", "eleSet1"));
        System.out.println(jedis.sismember("eleSet", "e2"));//true
        System.out.println(jedis.sismember("eleSet", "e22"));//false
    }


}
