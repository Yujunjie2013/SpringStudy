package com.example.validator_demo;

import com.example.validator_demo.pojo.TestInitMethod;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.support.GenericWebApplicationContext;

@SpringBootApplication
public class ValidatorDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ValidatorDemoApplication.class, args);
    }

    @Bean(initMethod = "myInit")
    public TestInitMethod testInitMethod() {
        return new TestInitMethod();
    }
}
