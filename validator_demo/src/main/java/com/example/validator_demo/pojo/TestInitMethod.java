package com.example.validator_demo.pojo;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

/**
 * 但一个类同时实现 InitializingBean 和指定 init-method时
 * ，执行顺序是先执行afterPropertiesSet，再执行自定义的init-method
 */
public class TestInitMethod implements InitializingBean, BeanPostProcessor {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("afterPropertiesSet");
    }


    public void myInit() {
        System.out.println("myInit");
    }

    public void test() {
        System.out.println("test");
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("after ===" + beanName + "---bean:" + bean);
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("before <<<<" + beanName + "---bean:" + bean);
        return bean;
    }
}
