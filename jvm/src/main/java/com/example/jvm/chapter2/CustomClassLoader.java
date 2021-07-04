package com.example.jvm.chapter2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * 自定义类加载器
 */
public class CustomClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] result = getClassByName(name);
        return defineClass(name, result, 0, result.length);
    }

    private byte[] getClassByName(String name) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(name);
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
}
