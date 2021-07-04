package com.example.jvm.chapter4;

public class StackFrameTest {
    public static void main(String[] args) {

    }

    public void method1(){
        System.out.println("method1执行");
        method2();
        System.out.println("method1执行结束");
    }

    private void method2() {
        System.out.println("method2开始执行");
    }
}
