package com.example.cache.controller;

import com.example.cache.bean.Student;
import com.example.cache.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("/add")
    public String addStudent(@RequestBody @Validated Student student) {
        boolean save = studentService.save(student);
        return save ? "保存成功" : "保存失败";
    }

    @GetMapping("/{id}")
    public Student queryById(@PathVariable Long id) {
        return studentService.getById(id);
    }

    @PutMapping("/{id}/{sex}")
    public Student updateById(@PathVariable Long id, @PathVariable String sex) {
        return studentService.updateSexById(id, sex);
    }

    @PutMapping("/ext/{id}/{sex}")
    public Integer updateByIdExt(@PathVariable Long id, @PathVariable String sex) {
        return studentService.updateSexByIdExt(id, sex);
    }

    @GetMapping("/set")
    public Integer setData() {
        Student student = studentService.getById(1);
        redisTemplate.opsForValue().set("info", student);
        return 1;
    }

    @DeleteMapping("/del/{id}")
    public Boolean deleteById(@PathVariable Long id){
        return studentService.removeById(id);
    }
}
