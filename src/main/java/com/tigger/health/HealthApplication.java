package com.tigger.health;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class HealthApplication {
    public static void main(String[] args) {
        SpringApplication.run(HealthApplication.class, args);
        log.info("项目成功运行。。。。。。。");
    }

}
