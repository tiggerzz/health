package com.tigger.health;

import com.tigger.health.entity.Files;
import com.tigger.health.entity.User;
import com.tigger.health.service.FilesService;
import com.tigger.health.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
@Slf4j
class HealthApplicationTests {

    @Autowired
    private UserService userService;

    @Autowired
    private FilesService filesService;

    @Test
    void contextLoads() {
        for (int i = 0; i< 10; i++) {
            Files test = new Files();
            test.setName("test1");
            test.setCheckDate("2023-1-1");
            filesService.save(test);
        }

    }

    @Test
    void setFilesService() {
        List<Files> list = filesService.list();
        log.info(list.toString());
    }

}
