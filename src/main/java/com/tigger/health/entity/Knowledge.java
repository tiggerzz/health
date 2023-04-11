package com.tigger.health.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "health_knowledge")
public class Knowledge {
    private String title;
    private String content;
}
