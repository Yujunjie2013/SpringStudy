package com.example.jvm.chapter2;

import sun.misc.Launcher;
import sun.security.ec.CurveDB;

import java.net.URL;
import java.security.Provider;

public class ClassLoaderTest2 {
    public static void main(String[] args) {

        System.out.println("-------启动类加载器————————————");
        final URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for (URL urL : urLs) {
            System.out.println(urL.toExternalForm());
        }
        System.out.println();
        //从上面的路径中的任意一个jar中找一个类加载
        System.out.println(Provider.class.getClassLoader());//null
        System.out.println();
        System.out.println("==========扩展类加载器============");
        System.out.println();
        final String property = System.getProperty("java.ext.dirs");
        System.out.println(property);
        final String[] split = property.split(";");
        for (String s : split) {
            System.out.println(s);
        }
        System.out.println();
        //从上面的路径中的任意一个jar中找一个类加载
        System.out.println(CurveDB.class.getClassLoader());//sun.misc.Launcher$ExtClassLoader@355da254

    }
}
