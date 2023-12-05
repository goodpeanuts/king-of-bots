package com.kingofboss.backend.service.impl.user.account;

import com.kingofboss.backend.mapper.UserMapper;
import com.kingofboss.backend.service.user.account.ResetPasswordService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
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
    public Map<String, String> reset(String username, String oldPassword, String password, String confirmPassword) {
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

        String url = "jdbc:mysql://localhost:3306/kob?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";
        String databaseUsername = "root";
        String databasePassword = "123456";
        oldPassword = DigestUtils.md5Hex(oldPassword);
        String sql = "SELECT * FROM user WHERE username = '" + username + "'" + " AND password = '" + oldPassword + "'";  // Replace with actual username and password
        try {
            Connection connection = DriverManager.getConnection(url, databaseUsername, databasePassword);
            System.out.println("Connected to the database");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println(sql);

            if (!resultSet.next()) {
                map.put("error_message", "用户名或密码错误");
                return map;
            }

            System.out.println("User ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
            Integer id = resultSet.getInt("id");
            System.out.println(resultSet.getInt("id"));

            // Update password
            password = DigestUtils.md5Hex(password);
            String updateSql = "UPDATE user SET password = '" + password + "' WHERE id = " + id;
            statement.executeUpdate(updateSql);
            System.out.println("Password updated successfully");


            // Close the connection

            connection.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database");
            e.printStackTrace();
        }

//        String jwt = JwtUtil.createJWT(user.getId().toString());
        System.out.println("reset!");
        map.put("error_message" ,"success");

        return map;

    }

}
