package com.example.cache.service;

import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cache.dao.StudentMapper;
import com.example.cache.bean.Student;

import java.io.Serializable;

@Service
@CacheConfig(cacheNames = "studentService")
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {

    /**
     * 当结果为Null的时候不更新缓存
     *
     * @param id
     * @param sex
     * @return
     */
    @Override
    @CachePut(key = "#id", unless = "#result==null")
    public Student updateSexById(Long id, String sex) {
        Integer integer = baseMapper.updateSexById(id, sex);
        return getById(id);
    }

    @CachePut
    @Override
    public Integer updateSexByIdExt(Long id, String sex) {
        return baseMapper.updateSexById(id, sex);
    }

    /**
     * 当结果为Null的时候不缓存
     *
     * @param id
     * @return
     */
    @Cacheable(key = "#id", unless = "#result==null")
    @Override
    public Student getById(Serializable id) {
        return getBaseMapper().selectById(id);
    }

    /**
     * 删除数据，同时删除缓存
     *
     * @param id
     * @return
     */
    @CacheEvict(key = "#id")
    @Override
    public boolean removeById(Serializable id) {

        return SqlHelper.retBool(getBaseMapper().deleteById(id));
    }
}
