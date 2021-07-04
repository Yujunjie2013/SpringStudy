package com.example.jvm.chapter4;

import java.util.Date;

/**
 * 本地变量表演示
 * 需要使用javap 命令
 * 或者使用jclasslib加载class文件
 */
public class LocalVariablesTest {
    public static int a = 111;
    public int a1 = 111;

    static {
        a = 22;
        b = 444;
    }

    {
        a1 = 22;
        b1 = 44;
    }

    public static int b = 333;
    public int b1 = 333;

    public static void main(String[] args) {
        final LocalVariablesTest localVariablesTest = new LocalVariablesTest();
        localVariablesTest.test1();
        System.out.println(a);
        System.out.println(b);
        System.out.println(localVariablesTest.a1);
        System.out.println(localVariablesTest.b1);
//        localVariablesTest.test4();
    }

    public static void testStatic() {
        String name = "俊杰";
        int age = 18;
        double weight = 68.5;
        System.out.println(age);
    }

    public static void testStatic2() {
        String name = "俊杰";
        int age = 18;
        double weight = 68.5;
        System.out.println(name + ":" + age + ":" + weight);
    }


    public void test1() {
        Date date = new Date();
        String name1 = "atguigu.com";
        String info = test2(date, name1);
        System.out.println(info);
    }

    public String test2(Date date, String str) {
        date = null;
        str = "yujunjie";
        double weight = 130.5;
        char gender = '男';
        return date + str;
    }


    //局部变量表中的槽位复用
    public void test3() {
        int a = 10;
        {
            int b = 1;
            b = a + 1;
        }
        int c = a + 1;
    }


    public void test4() {
        int num;
//        System.out.println(num);这里直接使用，编译器会报错
        // int[] a2 = new int[]{2};
        // System.out.println(a2[1]);
    }


}
