package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.Executors;

@SpringBootTest
class AppTests {

    @Test
    void contextLoads() {

        Executors.newFixedThreadPool(6);
    }

}
