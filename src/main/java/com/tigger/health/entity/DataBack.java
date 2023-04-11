package com.tigger.health.entity;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;

@Data
public class DataBack {
    private Long total;
    private List items;
}
