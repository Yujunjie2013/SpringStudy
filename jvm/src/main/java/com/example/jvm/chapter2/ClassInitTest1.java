package com.example.jvm.chapter2;

public class ClassInitTest1 {
    public static void main(String[] args) {
        System.out.println(Son.C);
        System.out.println(Son.B);
    }

    static class Father {
        public static int A = 1;

        static {
            A = 2;
            System.out.println("父类的静态代码块执行");
        }

    }

    static class Son extends Father {
        public static int C = 3;
        static int B = 2;

        static {
            System.out.println("子类的静态代码块执行，C=" + 3);
            System.out.printf("之类的静态%d，结果是%d\n", C, B);
        }
    }
}
