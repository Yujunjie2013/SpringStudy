package com.example.jvm.chapter2;

import sun.misc.Launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 自定义类加载器
 */
public class CustomClassLoader extends ClassLoader {
    private String path;

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] result = getClassByName(name);
        return defineClass(name, result, 0, result.length);
    }

    private byte[] getClassByName(String name) {
        FileInputStream fileInputStream = null;
        try {
            final String fileName = name.replace(".", "/");
//            fileInputStream = new FileInputStream(new File(path + name + ".class"));
            fileInputStream = new FileInputStream(new File(path+fileName + ".class"));
            byte[] data = new byte[fileInputStream.available()];
            fileInputStream.read(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new byte[0];
    }

    public void setPath(String path) {
        this.path = path;
    }
}
