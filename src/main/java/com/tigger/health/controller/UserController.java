package com.tigger.health.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tigger.health.entity.User;
import com.tigger.health.service.UserService;
import com.tigger.health.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    private R login(@RequestBody User user, HttpServletRequest request) {
        LambdaQueryWrapper<User> query = new LambdaQueryWrapper<User>();
        query.eq(User::getUsername, user.getUsername());
        User one = userService.getOne(query);
        if (user.getPassword().equals(one.getPassword())) {
            request.getSession().setAttribute("user", one.getUsername());
            request.getSession().setAttribute("name", one.getName());
            return R.ok().put("token", one.getToken());
        } else {
            return R.error(0, "密码错误，请重新输入");
        }
    }

    @GetMapping("/list")
    private R userList() {
        List<User> users = userService.list();
        List<String> name = users.stream().map(e -> e.getUsername()).collect(Collectors.toList());
        return R.ok().put("users", name);
    }

    @PutMapping("/create")
    private R createUser(@RequestBody User user) {
        userService.save(user);
        return R.ok();
    }

    @PostMapping("/pwd")
    private R changePwd(@RequestBody Map<String, String> map, HttpServletRequest request) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.getSession().getAttribute("user")));
        if (user.getPassword().equals(map.get("newPwd"))) {
            return R.error(0, "新密码不能与原密码一致!");
        }
        if (user.getPassword().equals(map.get("originalPwd"))) {
            if (map.get("newPwd").equals(map.get("confirmPwd"))) {
                user.setPassword(map.get("confirmPwd"));
                userService.updateById(user);
                return R.ok();
            } else {
                return R.error(0, "两次密码不一致！");
            }
        } else {
            return R.error(0, "原密码错误！");
        }
    }

    @GetMapping("/info")
    private R getUser(HttpServletRequest request) {
        User user = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.getSession().getAttribute("user")));
        return R.ok().put("user", user);
    }

    @PostMapping("/change")
    private R changeNameEmail(@RequestBody User user, HttpServletRequest request) {
        User userNow = userService.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, request.getSession().getAttribute("user")));
        userNow.setEmail(user.getEmail());
        userNow.setName(user.getName());
        userService.updateById(userNow);
        request.getSession().setAttribute("name", user.getName());
        return R.ok();
    }


}
