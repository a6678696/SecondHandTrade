package com.ledao;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author LeDao
 */
@MapperScan("com.ledao.mapper")
@SpringBootApplication
public class SecondHandTradeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondHandTradeApplication.class, args);
    }

}
