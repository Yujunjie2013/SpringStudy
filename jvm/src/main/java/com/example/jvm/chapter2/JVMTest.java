package com.example.jvm.chapter2;

public class JVMTest {
    private static int num = 1;

    static {
        num = 3;
        number = 10;
        System.out.println("静态代码块加载num:"+num);
//        System.out.println(number);//报错，非法的前线向引用
    }

    private static int number = 20;
    private int a = 1;

    public JVMTest() {
//        a = 2;
//        b = 50;
    }
    {
        a=3;
        b=40;
        System.out.println("代码块加载了a:"+a);
//        System.out.println(b);//报错
    }
    private int b = 100;

    public JVMTest(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {
//        Launcher
        System.out.println(num);
        System.out.println(number);
        final JVMTest jvmTest = new JVMTest();
        System.out.println(jvmTest.a);
        System.out.println(jvmTest.b);
        final JVMTest jvmTest2 = new JVMTest();
        System.out.println("==============");
        System.out.println(jvmTest2.a);
        System.out.println(jvmTest2.b);
    }
}
