package com.example.jvm.chapter2;

import java.test.jvm.TestS;

public class ClassLoaderTest3 {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        CustomClassLoader customClassLoader = new CustomClassLoader();
        customClassLoader.setPath("/Users/yujunjie/Desktop/cls/");
//        customClassLoader.setPath("");
        final Class<?> aClass = customClassLoader.loadClass("JVMTest");
        final Object o = aClass.newInstance();
        System.out.println(o.getClass().getClassLoader());

    }
}
