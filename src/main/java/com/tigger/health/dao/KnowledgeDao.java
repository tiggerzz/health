package com.tigger.health.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tigger.health.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KnowledgeDao extends BaseMapper<Knowledge> {
}
