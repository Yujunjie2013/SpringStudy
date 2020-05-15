package com.example.cache.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cache.bean.Student;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper extends BaseMapper<Student> {
    Integer updateSexById(@Param("id") Long id, @Param("sex") String sex);
}