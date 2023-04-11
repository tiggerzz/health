package com.tigger.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigger.health.dao.FilesDao;
import com.tigger.health.entity.Files;
import com.tigger.health.service.FilesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilesServiceImpl extends ServiceImpl<FilesDao, Files> implements FilesService {

    public void changeNames(String name) {
        List<Files> list = this.list();
        list.forEach(files -> files.setName(name));
        this.updateBatchById(list);
    }

}
