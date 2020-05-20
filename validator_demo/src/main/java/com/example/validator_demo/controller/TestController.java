package com.example.validator_demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.validator_demo.pojo.TestParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

//    @Value("${junjie.test}")
//    String test;
//    @Value("${junjie.uname}")
    private String UName;

    @Value("${spring.application.name}")
    String name;

    @Value("${server.port}")
    private String port;

    @GetMapping("/get")
    public String get(){
        return "成功了"+port;
    }

    @PostMapping("/post")
    public String test(@RequestBody @Validated TestParams testParams) {
        return "成功了";
    }


//    @GetMapping()
//    public String get() {
//        System.out.println("获取到test的值:" + test);
//        return "Get 请求--test:"+test+"---uName:"+UName;
//    }


    public static void main(String[] args) {
        TestParams testParams = new TestParams();
        testParams.setAge(13);
        testParams.setBirthday(new Date());
        testParams.setSubscribeTime(new Date());
        testParams.setEmail("999@qq.com");
        testParams.setHeight(1.34f);
        testParams.setIsGril(true);
        testParams.setIsStudent(false);
        testParams.setUserName("俊杰");
        testParams.setId(1L);
        testParams.setUserPhone("13522222222");
        List<String> strings = Arrays.asList("哈哈", "黑");
        testParams.setInfoList(strings);
        String string = JSONObject.toJSONString(testParams);
        System.out.println(string);
    }
}
