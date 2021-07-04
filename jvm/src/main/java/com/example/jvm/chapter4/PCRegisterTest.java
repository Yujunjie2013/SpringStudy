package com.example.jvm.chapter4;

/**
 * 进入目录： /Users/yujunjie/workspace/demo/SpringStudy/jvm/target/classes/com/example/jvm/chapter4
 * 执行 javap -v PCRegisterTest 命令
 */
public class PCRegisterTest {
    public static void main(String[] args) {
        int a = 10;
        int j = 20;
        int k = a + j;
        String b = "我是字符串";
        System.out.println(k);
        System.out.println(b);
    }
}
