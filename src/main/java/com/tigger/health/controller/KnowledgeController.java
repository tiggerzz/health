package com.tigger.health.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tigger.health.entity.DataBack;
import com.tigger.health.entity.Files;
import com.tigger.health.entity.Knowledge;
import com.tigger.health.service.KnowledgeService;
import com.tigger.health.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/knowledge")
public class KnowledgeController {

    @Autowired
    private KnowledgeService knowledgeService;

    @GetMapping("/list")
    public R getList(int page, int limit, String title) {
        LambdaQueryWrapper<Knowledge> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(Knowledge::getTitle, title);
        IPage iPage = new Page(page, limit);
        if (title == null) {
            IPage list = knowledgeService.getBaseMapper().selectPage(iPage, null);
            DataBack data = new DataBack();
            data.setTotal(list.getTotal());
            data.setItems(list.getRecords());
            return R.ok().put("data", data);
        } else {
            IPage list = knowledgeService.getBaseMapper().selectPage(iPage, lambdaQueryWrapper);
            DataBack data = new DataBack();
            data.setTotal(list.getTotal());
            data.setItems(list.getRecords());
            return R.ok().put("data", data);
        }
    }
}
