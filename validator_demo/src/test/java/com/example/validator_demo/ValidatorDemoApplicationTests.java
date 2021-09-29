package com.example.validator_demo;

import com.example.validator_demo.pojo.TestInitMethod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ValidatorDemoApplicationTests {


    @Autowired
    private TestInitMethod testInitMethod;

    @Test
    void contextLoads() {
        testInitMethod.test();
    }

}
