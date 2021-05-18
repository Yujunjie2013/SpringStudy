package com.test.rabbit.config;

import org.springframework.util.Base64Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;


/**
 * AES加密工具类
 *
 * @author monkey
 */
public class AesEncryptUtils {
    //可配置到Constant中，并读取配置文件注入,16位,自己定义
    private static final String KEY = "KitedgeChips1102";
    private static final String IV_KEY = "0011223344abcdef";

    //参数分别代表 算法名称/加密模式/数据填充方式
    private static final String ALGORITHMSTR = "AES/CBC/PKCS5Padding";
    private static Cipher cipher;
    private static IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度
    private static SecretKeySpec secretKeySpec = new SecretKeySpec(KEY.getBytes(), "AES");

    static {
        try {
            cipher = Cipher.getInstance(ALGORITHMSTR);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 加密
     *
     * @param content 加密的字符串
     * @return
     * @throws Exception
     */
    public static String encrypt(String content) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, iv);
            byte[] b = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
            // 采用base64算法进行转码,避免出现中文乱码
            return Base64Utils.encodeToString(b);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 解密
     *
     * @param encryptStr 解密的字符串
     * @return
     * @throws Exception
     */
    public static String decrypt(String encryptStr) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, iv);
            // 采用base64算法进行转码,避免出现中文乱码
            byte[] encryptBytes = Base64Utils.decode(encryptStr.getBytes());
            byte[] decryptBytes = cipher.doFinal(encryptBytes);
            return new String(decryptBytes);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return encryptStr;
    }

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 1000; i++) {
            Student student = new Student("张三:" + i, i);
            new Thread(new Runnable() {
                @Override
                public void run() {

                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("年纪:" + student.age);
                }
            }).start();
        }
    }

    public static class Student {
        public String name;
        public int age;

        public Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}