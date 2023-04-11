package com.tigger.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tigger.health.entity.Files;
import org.springframework.beans.factory.annotation.Autowired;

public interface FilesService extends IService<Files> {
    void changeNames(String name);
}
