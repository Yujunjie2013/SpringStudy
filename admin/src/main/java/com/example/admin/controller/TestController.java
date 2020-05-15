package com.example.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/{id}")
    public String test(@PathVariable String id) {
        return "返回结果:" + id;
    }

    @GetMapping("/get")
    public String test() {
        return "返回结果:get";
    }
}
