package com.example.validator_demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.validator_demo.pojo.TestParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /**
     * 线程池
     */
    public static ExecutorService FIXED_THREAD_POOL = Executors.newFixedThreadPool(10);
    @GetMapping("/deferredresult")
    public DeferredResult<String> deferredResult() throws Exception {
        System.out.println("控制层执行线程:" + Thread.currentThread().getName());
        //超时
        DeferredResult<String> deferredResult = new DeferredResult<String>(10*1000L);
        deferredResult.onTimeout(new Runnable() {
            @Override
            public void run() {
                System.out.println("异步线程执行超时");
                deferredResult.setResult("线程执行超时");
            }
        });
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                System.out.println("异步执行完毕");
            }
        });
        FIXED_THREAD_POOL.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("异步执行线程:" + Thread.currentThread().getName());
                try {
                    int k = 1;
                    System.out.println("------------------------在看鱼，不要打扰--------------");
                    Thread.sleep(1000);
                    Thread.sleep(3000);
                    deferredResult.setResult("这是【异步】的请求返回: " + k);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return deferredResult;
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
