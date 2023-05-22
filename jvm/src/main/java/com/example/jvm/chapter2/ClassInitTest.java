package com.example.jvm.chapter2;

public class ClassInitTest {
    public static int num = 1;
    public int a = 1;

    static {
        num = 3;
        number = 20;
    }

    {
        a = 2;
        num = 12;
        number = 22;
        System.out.println("执行代码块");
    }

    public static int number = 10;

    public static void main(String[] args) {
        System.out.println(num);
        System.out.println(number);
        ClassInitTest classLoaderTest = new ClassInitTest();
        System.out.println(classLoaderTest.a);
        System.out.println(classLoaderTest.num);
        System.out.println(ClassInitTest.number);
    }
}
