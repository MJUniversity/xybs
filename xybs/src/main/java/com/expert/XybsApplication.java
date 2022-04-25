package com.expert;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.expert.xybs.mapper")
public class XybsApplication {

    public static void main(String[] args) {
        SpringApplication.run(XybsApplication.class, args);
    }

}
