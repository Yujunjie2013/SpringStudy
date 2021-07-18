package com.example.jvm.chapter9;

import java.io.Serializable;
import java.lang.reflect.Constructor;

public class MethodInnerStrucTest extends Object implements Comparable<String>, Serializable {

    public int num = 10;
    private static String str = "测试方法的内部结构";

    private final static int a = 20;
    private String b = "测试方法的内部结构";

    //构造器
    //方法
    public void test1() {
        int count = 20;
        System.out.println("count = " + count);
    }

    public static int test2(int cal) {
        int result = 0;
        try {
            int value = 30;
            result = value / cal;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public int compareTo(String o) {
        return 0;
    }

    public static void main(String[] args) {
        int x = 500;
        int y = 100;
        try {
            int a = x / y;
        } catch (Exception e) {
            e.printStackTrace();
        }
        int b = 50;
        int c = b + b;
        System.out.println(c);

    }
}
