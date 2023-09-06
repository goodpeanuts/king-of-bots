package com.kingofboss.backend.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.backend.mapper.UserMapper;
import com.kingofboss.backend.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.security.util.Password;

import java.util.List;

@RestController
public class UserController {

    //如果要用到数据库中的 mapper 一定要加下面的注释 @Autowired
    @Autowired
    UserMapper userMapper;
    @RequestMapping("/user/all/")
    public List<User> getAll() {
    return userMapper.selectList(null);
    }

    @GetMapping("/user/{userId}/")
    public User getuer(@PathVariable int userId) {
        // 复杂的查询要用到queryWrapper
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);

        return userMapper.selectOne(queryWrapper);
    }

    // 插入用户信息，这里为了方便调试用get的方式
    @GetMapping("/user/add/{userId}/{username}/{password}/")
    public String addUser (
            @PathVariable int userId,
            @PathVariable String username,
            @PathVariable String password) {
        if (password.length() < 6) {
            return "密码太短";
        }
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userId, username, encodedPassword);
        userMapper.insert(user);
        return "add user Successfully";
    }

    @GetMapping("/user/delete/{userId}/")
    public String deleteUser (@PathVariable int userId) {
        userMapper.deleteById(userId);
        return "delete User successfully";
    }
}
