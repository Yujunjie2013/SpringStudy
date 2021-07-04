package com.example.jvm.chapter4;

public class DynamicLinkingTest {
    int num;

    public void methodA() {
        System.out.println("methodA");
    }

    public void methodB() {
        System.out.println("methodB");
        methodA();
        num++;
    }
}
