package com.tigger.health.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tigger.health.entity.Files;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FilesDao extends BaseMapper<Files> {
}
