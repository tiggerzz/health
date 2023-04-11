package com.tigger.health.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tigger.health.dao.UserDao;
import com.tigger.health.entity.User;
import com.tigger.health.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
}
