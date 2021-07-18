package com.example.jvm.chapter8;

/**
 * -XX:NewRatio 设置新生代与老年代的比例，默认是2
 * -XX:SurvivorRatio 设置新生代中eden / survivor 区的比例
 *
 * -XX:-UseAdaptiveSizePolicy:关闭自适应的内存分配策略
 * -Xmn 指定新生代内存大小，如果用了该配置，则-XX:NewRatio失效，以-Xmn为准
 */
public class EdenSurvivorTest {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("来过");
        Thread.sleep(Integer.MAX_VALUE);
    }
}
