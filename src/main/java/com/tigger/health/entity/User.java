package com.tigger.health.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName(value = "health_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String name;

    private String email;

    private String token;

}
