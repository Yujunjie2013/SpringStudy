package com.test.rabbit.config;

import cn.hutool.core.io.IoUtil;
import cn.hutool.crypto.symmetric.AES;
import com.google.gson.Gson;
import com.test.rabbit.bean.Student;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

public class DecryptHttpInputMessage implements HttpInputMessage {
    private HttpInputMessage inputMessage;
    private String charset;
    private AES aes;
    private Long timeDifference;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String charset, AES crypto, Long timeDifference) {
        this.inputMessage = inputMessage;
        this.charset = charset;
        this.aes = crypto;
        this.timeDifference = timeDifference;
    }

    @Override
    public InputStream getBody() throws IOException {
        String content = IoUtil.read(inputMessage.getBody(), charset);
        try {
            String s = aes.decryptStr(content);
            return new ByteArrayInputStream(s.getBytes());
        } catch (Exception e) {
            throw new RuntimeException("非法的请求参数");
        }
    }

    @Override
    public HttpHeaders getHeaders() {
        return inputMessage.getHeaders();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(System.currentTimeMillis());
        String abcd = "abcdef00poiuytr0";
        AES aes = new AES(abcd.getBytes());
        Student student = new Student();
        student.setAge(111);
        student.setName("张三");
        student.setPhone("133");
        String s = new Gson().toJson(student);
        System.out.println(s);

        String s1 = aes.encryptBase64(s.getBytes(StandardCharsets.UTF_8));
        System.out.println("加密:" + s1 + "====>长度:" + s1.length());
        String s2 = aes.decryptStr(s1);
        System.out.println("解密:" + s2);
    }
}
