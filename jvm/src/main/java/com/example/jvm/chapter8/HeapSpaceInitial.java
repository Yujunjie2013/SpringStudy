package com.example.jvm.chapter8;

/**
 * 设置对空间大小的参数
 * -Xms 用来设置堆空间（年轻代+老年代）的 初始化 内存大小
 *  -X 是jvm的运行参数
 *   ms 是 memory start
 *
 * -Xmx 用来设置堆空间（年轻代+老年代）的初 最大 内存大小
 *
 * 默认情况下
 *  初始化内存大小:物理电脑内存大小/64
 *  最大内存大小:物理电脑内存/4
 *
 * 手动设置；-Xms600m -Xmx600m
 *  开发中建议将初始化堆内存和最大的堆内存设置成相同的值，避免频繁的扩容和释放
 *
 * 如何查看设置的参数：方式1 jps
 *                        jstat -gc 进程id
 *
 *                   方式2 -XX:+PrintGCDetails
 *
 */
public class HeapSpaceInitial {
    public static void main(String[] args) {
        //返回Java虚拟机中的堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;
        //返回Java虚拟机试图使用的最大堆内存
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms :" + initialMemory + "M");
        System.out.println("-Xmx :" + maxMemory + "M");

        System.out.println("系统的内存大小为:" + initialMemory * 64 / 1024 + "G");
        System.out.println("系统的内存大小为:" + maxMemory * 4 / 1024 + "G");
    }
}
