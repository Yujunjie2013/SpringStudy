package com.example.mvcframework.service.impl;

import com.example.mvcframework.annotation.GPService;
import com.example.mvcframework.service.ITestService;

@GPService
public class TestServiceImpl implements ITestService {
    @Override
    public String testMVC() {
        System.out.println("使用自动注入调用Service 方法");
        return "测试Service 的自动注入";
    }
}
