package com.test.rabbit.controller;

import com.test.rabbit.service.ConfirmProvider;
import com.test.rabbit.service.ConfirmProviderExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ConfirmProviderExt confirmProvider;

    @GetMapping("/{msg}")
    public String sendMsg(@PathVariable String msg) {
        confirmProvider.publisMessage(msg);
        return "消息发送成功";
    }
}
