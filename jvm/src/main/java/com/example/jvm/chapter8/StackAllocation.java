package com.example.jvm.chapter8;

/**
 * 栈上分配测试
 * <p>
 * -Xms1G -Xmx1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 * <p>
 * -XX:-DOEscapeAnalysis  不开启逃逸分析
 * -XX:+DOEscapeAnalysis  开启逃逸分析
 *
 * 分别开启和不开启运行后使用JVisualVM查看堆内存中的个数
 *
 */
public class StackAllocation {
    public static void main(String[] args) {
        final long l = System.currentTimeMillis();
        int i = 0;
        while (i < 10000000) {
            alloc();
            i++;
        }

        System.out.println(System.currentTimeMillis() - l + "毫秒");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void alloc() {
        StackAllocation stackAllocation = new StackAllocation();//未发生逃逸
    }
}
