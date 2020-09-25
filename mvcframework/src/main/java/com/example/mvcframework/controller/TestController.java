package com.example.mvcframework.controller;

import com.example.mvcframework.annotation.GPAutowired;
import com.example.mvcframework.annotation.GPController;
import com.example.mvcframework.annotation.GPRequestMapping;
import com.example.mvcframework.service.ITestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@GPController
@GPRequestMapping("/demo")
public class TestController {

    @GPAutowired
    private ITestService iTestService;


    @GPRequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("我是TestController 中的 方法");
        return iTestService.testMVC();
    }
}
