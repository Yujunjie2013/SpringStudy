package com.example.jvm.chapter2;

import sun.misc.Launcher;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class ClassLoaderTest {
    public static void main(String[] args) {
        final ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
        final ClassLoader parent = systemClassLoader.getParent();
//        Launcher.ExtClassLoader
//        Launcher.AppClassLoader
        System.out.println(parent);

        //继续获取上层
        final ClassLoader bootStrapClassLoader = parent.getParent();
        System.out.println(bootStrapClassLoader);//null

        //对于用户自定义的类使用的是 默认使用 系统类加载器
        System.out.println("用户自定义类的加载器是:" + ClassLoaderTest.class.getClassLoader());

        //系统的核心类库使用的是 引导类加载器
        System.out.println("系统类String的类加载器是:" + String.class.getClassLoader());//null

        System.out.println(ReentrantLock.class.getClassLoader());
        System.out.println(HashMap.class.getClassLoader());
    }
}
