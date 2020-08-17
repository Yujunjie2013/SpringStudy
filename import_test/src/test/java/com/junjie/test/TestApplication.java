package com.junjie.test;

import com.junjie.test.bean.User;
import com.junjie.test.config.EnableUserBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;

@EnableUserBean
public class TestApplication {
    /**
     * EnableUserBean--->UserImportSelector--->UserConfiguration
     * @param args
     */
    public static void main(String[] args) {
        //获取Spring容器
//        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestApplication.class);
//        User bean = ac.getBean(User.class);
//        System.out.println(bean);

    }
}
