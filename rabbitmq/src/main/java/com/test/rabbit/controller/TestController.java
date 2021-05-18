package com.test.rabbit.controller;

import com.google.gson.Gson;
import com.test.rabbit.bean.Student;
import com.test.rabbit.service.ConfirmProviderExt;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
    @Autowired
    private ConfirmProviderExt confirmProvider;

    @GetMapping("/{msg}")
    public String sendMsg(@PathVariable String msg) {
        confirmProvider.publisMessage(msg);
        return "消息发送成功";
    }
    @GetMapping("dlx/{msg}")
    public String senddlxMsg(@PathVariable String msg) {
        confirmProvider.publisDlXMessage(msg);
        return "向死信队列发送消息成功";
    }
    @GetMapping("fan/{msg}")
    public String sendfanMsg(@PathVariable String msg) {
        confirmProvider.publisFanoutMessage(msg);
        return "向死信队列发送消息成功";
    }
    @PostMapping("/body")
    public String send(@RequestBody Student student) {
        Gson gson = new Gson();
        String s = gson.toJson(student);
        log.info("请求成功{}", s);
        return "请求成功";
    }

}
