package com.kingofboss.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.backend.mapper.UserMapper;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.user.account.RegisterService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Map<String, String> register(String username, String password, String confirmPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if (password == null || confirmPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        // 删除末尾空格
        username = username.trim();
        //password = password.trim();

        if(username.length() == 0) {
            map.put("error_message", "用户名不能为空");
            return map;
        }

        if (password.length() == 0 || confirmPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "用户名长度不能超过100");
            return map;
        }

        if (password.length() > 100) {
            map.put("error_message", "密码长度不能超过100");
            return map;
        }

        if (!password.equals(confirmPassword)) {
            map.put("error_message", "两次输入密码不一致");
            return map;
        }

        // 检查密码强度
//        if (password.length() < 8) {
//            map.put("error_message", "密码长度不能小于8");
//            return map;
//        }

//        boolean hasDigit = false;
//        boolean hasLetter = false;
//        for (int i = 0; i < password.length(); i++) {
//            if (Character.isDigit(password.charAt(i))) {
//                hasDigit = true;
//            }
//            if (Character.isLetter(password.charAt(i))) {
//                hasLetter = true;
//            }
//        }
//        if (!hasDigit || !hasLetter) {
//            map.put("error_message", "密码必须包含数字和字母");
//            return map;
//        }

        // 查询用户名是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()){
            map.put("error_message", "用户已存在");
            return map;
        }

        // 添加一个新用户
//        String encodedPassword = passwordEncoder.encode(password);
        // md5加密
        String encodedPassword = DigestUtils.md5Hex(password);

        // 输入图片地址
        String photo = "https://cdn.acwing.com/media/user/profile/photo/239572_lg_a4f5e874d6.jpg";
        User user = new User(null, username, encodedPassword, photo, 1500);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;

    }
}
