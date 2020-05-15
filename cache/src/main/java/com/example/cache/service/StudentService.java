package com.example.cache.service;

import com.example.cache.bean.Student;
import com.baomidou.mybatisplus.extension.service.IService;


public interface StudentService extends IService<Student> {


    Student updateSexById(Long id, String sex);

    Integer updateSexByIdExt(Long id, String sex);
}
