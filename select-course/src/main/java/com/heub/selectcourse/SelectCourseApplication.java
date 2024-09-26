package com.heub.selectcourse;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 秦乾正
 */
@SpringBootApplication
@EnableScheduling
@MapperScan("com.heub.selectcourse.mapper")
public class SelectCourseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SelectCourseApplication.class, args);
    }

}
