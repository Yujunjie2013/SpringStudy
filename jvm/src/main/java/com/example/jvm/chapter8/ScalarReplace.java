package com.example.jvm.chapter8;

/**
 * 标量替换测试
 * -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-EliminateAllocations
 * <p>
 * -XX:-EliminateAllocations 不开启标量替换
 * -XX:+EliminateAllocations 开启标量替换
 */
public class ScalarReplace {

    static class User {
        public int age;
        public String name;
    }

    public static void main(String[] args) {
        final long l = System.currentTimeMillis();
        int i = 0;
        while (i < 10000000) {
            alloc();
            i++;
        }
        System.out.println(System.currentTimeMillis() - l + ":毫秒");
    }

    public static void alloc() {
        final User user = new User();
        user.age = 11;
        user.name = "李四";
    }
}
