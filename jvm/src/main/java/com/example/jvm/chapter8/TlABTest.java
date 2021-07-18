package com.example.jvm.chapter8;

/**
 * 测试是否默认开启了TLAB
 * 1、运行程序
 * 2、打开命令行工具，使用jps命令查看所有的Java进程
 * 3、使用jinfo -flag UseTLAB {进程号} 查看是否开启了该参数
 */
public class TlABTest {
    public static void main(String[] args) {
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
