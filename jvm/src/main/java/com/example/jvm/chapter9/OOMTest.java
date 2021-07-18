package com.example.jvm.chapter9;


import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;

/**
 * jdk6中
 * -XX:PermSize=10m -XX:MaxPermSize=10m
 *
 * jdk8中
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 */
public class OOMTest extends ClassLoader {
    public static void main(String[] args) {
        int j = 0;
        try {
            final OOMTest oomTest = new OOMTest();
            for (int i = 0; i < 10000; i++) {
                //创建ClassWriter，用于生成二进制字节码
                ClassWriter classWriter = new ClassWriter(0);
                //指明版本号，修饰符、类名、报名、接口
                classWriter.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC, "Class" + i,
                        null, "java/lang/Object", null);
                byte[] code = classWriter.toByteArray();
                //类的加载
                oomTest.defineClass("Class" + i, code, 0, code.length);//Class对象
                j++;
            }
        } finally {
            System.out.println(j);
        }

    }
}
