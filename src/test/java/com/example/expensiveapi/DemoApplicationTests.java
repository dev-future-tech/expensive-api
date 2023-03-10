package com.example.expensiveapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private ExpensiveController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
