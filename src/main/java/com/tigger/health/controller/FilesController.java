package com.tigger.health.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tigger.health.entity.DataBack;
import com.tigger.health.entity.Files;
import com.tigger.health.service.FilesService;
import com.tigger.health.utils.QiniuUtils;
import com.tigger.health.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/files")
public class FilesController {

    @Autowired
    private FilesService filesService;

    @Autowired
    private QiniuUtils qiniuUtils;

    @GetMapping("/list")
    public R getList(int page, int limit, String name, HttpServletRequest request) {
        filesService.changeNames(request.getSession().getAttribute("name").toString());
        LambdaQueryWrapper<Files> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        IPage iPage = new Page(page, limit);
        if (name == null) {
            lambdaQueryWrapper.orderByAsc(Files::getCheckDate).eq(Files::getUsername, request.getSession().getAttribute("user"));
            IPage list = filesService.getBaseMapper().selectPage(iPage, lambdaQueryWrapper);
            DataBack data = new DataBack();
            data.setTotal(list.getTotal());
            data.setItems(list.getRecords());
            return R.ok().put("data", data);
        } else {
            lambdaQueryWrapper.orderByAsc(Files::getCheckDate).like(Files::getName, name);
            IPage list = filesService.getBaseMapper().selectPage(iPage, lambdaQueryWrapper);
            DataBack data = new DataBack();
            data.setTotal(list.getTotal());
            data.setItems(list.getRecords());
            return R.ok().put("data", data);
        }
    }

    @GetMapping("/chart")
    public R getList(HttpServletRequest request) {
        List<Files> list = filesService.list(new LambdaQueryWrapper<Files>().eq(Files::getUsername, request.getSession().getAttribute("user")));
        list.stream().forEach(file -> file.setName(request.getSession().getAttribute("name").toString()));
        DataBack data = new DataBack();
        data.setItems(list);
        return R.ok().put("data", data);
    }

    @GetMapping("/detail")
    public R getDetail(Long id) {
        Files detail = filesService.getById(id);
        return R.ok().put("data", detail);
    }

    @DeleteMapping("/delete")
    public R deleteById(Long id) {
        filesService.removeById(id);
        return R.ok();
    }

    @PutMapping("/create")
    public R createForm(@RequestBody Files file, HttpServletRequest request) {
        file.setName(request.getSession().getAttribute("name").toString());
        file.setUsername(request.getSession().getAttribute("user").toString());
        if (filesService.getById(file.getId()) != null) {
            filesService.updateById(file);
        } else {
            filesService.save(file);
        }
        return R.ok();
    }

    @PostMapping("/upload")
    public R upload(MultipartFile file) {
        String imgName = QiniuUtils.upload(file);
        if (imgName != null) {
            return R.ok(imgName);
        }
        return R.error();
    }

    @DeleteMapping("/deleteImg")
    public R deleteImg(String key) {
        Integer code = QiniuUtils.delete(key);
        if (code == 20000) {
            return R.ok();
        }
        return R.error(0, "failed");
    }


    @GetMapping("/download")
    public R download(String name) throws IOException {
        String[] arr = name.split(",");
        List<String> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            result.add(QiniuUtils.download(arr[i]));
        }
        return R.ok().put("url", result);
    }

    @PostMapping("/compare")
    public R getTwo(@RequestBody List<Files> two) {
        /*int match = two.get(0).getCheckDate().compareTo(two.get(1).getCheckDate());
        if (match > 0) {
            Files temp = new Files();
            temp = two.get(0);
            two.set(0, two.get(1));
            two.set(1, temp);
        }
        return R.ok().put("two", two);*/
        Files temp = new Files();
        for (int i = 0; i < two.size() - 1; i++) {
            for (int j = 0; j < two.size() - 1 - i; j++) {
                if (two.get(j).getCheckDate().compareTo(two.get(j+1).getCheckDate()) > 0) {
                    temp = two.get(j);
                    two.set(j, two.get(j+1));
                    two.set(j+1, temp);
                }
            }
        }
        return R.ok().put("two", two);
    }


}
