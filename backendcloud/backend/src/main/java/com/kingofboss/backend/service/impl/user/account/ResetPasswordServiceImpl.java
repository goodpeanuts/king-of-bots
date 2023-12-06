package com.kingofboss.backend.service.impl.user.account;

import com.kingofboss.backend.mapper.UserMapper;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.account.ResetPasswordService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

@Service
public class ResetPasswordServiceImpl implements ResetPasswordService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> reset(String password, String confirmPassword) {

        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailImpl loginUser = (UserDetailImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        if (password == null || confirmPassword == null) {
            map.put("error_message", "密码不能为空");
            return map;
        }

        if (password.length() == 0 || confirmPassword.length() == 0) {
            map.put("error_message", "密码不能为空");
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

//        检查密码强度
        if (password.length() < 8) {
            map.put("error_message", "密码长度不能小于8");
            return map;
        }

        boolean hasDigit = false;
        boolean hasLetter = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            }
            if (Character.isLetter(password.charAt(i))) {
                hasLetter = true;
            }
        }
        if (!hasDigit || !hasLetter) {
            map.put("error_message", "密码必须包含数字和字母");
            return map;
        }


        String encodedPassword = passwordEncoder.encode(password);

        user.setPassword(encodedPassword);
        userMapper.updateById(user);

        System.out.println("reset!");
        map.put("error_message" ,"success");

        return map;

    }
}
