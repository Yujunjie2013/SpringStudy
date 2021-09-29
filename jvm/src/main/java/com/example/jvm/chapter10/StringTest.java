package com.example.jvm.chapter10;

public class StringTest {
    public static void main(String[] args) {
        final long start = System.currentTimeMillis();
//        method1(100000);//4031 毫秒
//        method2(100000);//7 毫秒
        method3(100000);//6 毫秒
        final long end = System.currentTimeMillis();
        System.out.println("耗时:" + (end - start));
    }

    public static void testStr() {
        String a1 = "a1";
        String a11 = new String("a1");
        System.out.println(a1 == a11);//false

        final String a2 = "a1";
        String a12 = new String("a1");
        System.out.println(a2 == a12);

        String a3 = "ab";

        final String a4 = "a";
        String a5 = a4 + "b";
        System.out.println(a3 == a5);
    }

    public static void method1(int highLevel) {
        String str = "";
        for (int i = 0; i < highLevel; i++) {
            str = str + "a";
        }
    }

    public static void method2(int highLevel) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < highLevel; i++) {
            stringBuilder.append("a");
        }
    }

    public static void method3(int highLevel) {
        //方式3是2的一种优化方式，可以避免扩容带来的性能消耗
        StringBuilder stringBuilder = new StringBuilder(highLevel * 2);
        for (int i = 0; i < highLevel; i++) {
            stringBuilder.append("a");
        }
    }
}
