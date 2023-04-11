package com.tigger.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigger.health.dao.KnowledgeDao;
import com.tigger.health.entity.Knowledge;
import com.tigger.health.service.KnowledgeService;
import org.springframework.stereotype.Service;

@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeDao, Knowledge> implements KnowledgeService {
}
