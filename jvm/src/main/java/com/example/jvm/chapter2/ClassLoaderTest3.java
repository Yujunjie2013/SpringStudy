package com.example.jvm.chapter2;

import java.test.jvm.TestS;

public class ClassLoaderTest3 {
    public static void main(String[] args) {
        //
        System.out.println(TestS.class.getClassLoader());
    }
}
