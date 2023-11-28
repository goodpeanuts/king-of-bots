package com.kingofboss.backend.service.impl.user.account;

import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.user.account.LoginService;
import com.kingofboss.backend.utils.JwtUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

// 实现service此处添加注解
@Service
public class LoginServiceImpl implements LoginService {
    // 此处按住alt + insert 自动生成实现


    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> getToken(String username, String password) {
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(username, password);
//        // 在authenticate 后输入 .var 自动生成变量名与类型
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken); // 登录失败会自动处理
//
//        UserDetailImpl loginUser = (UserDetailImpl) authenticate.getPrincipal();
//        User user = loginUser.getUser();
        String jwt = "";
        String url = "jdbc:mysql://localhost:3306/kob?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8";
        String databaseUsername = "root";
        String databasePassword = "123456";
        password = DigestUtils.md5Hex(password);
        String sql = "SELECT * FROM user WHERE username = '" + username + "'" + " AND password = '" + password + "'";  // Replace with actual username and password
        try {
            Connection connection = DriverManager.getConnection(url, databaseUsername, databasePassword);
            System.out.println("Connected to the database");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);


            if (!resultSet.next()) {
                Map<String, String> map = new HashMap<>();
                map.put("error_message", "用户名或密码错误");
                return map;
            }

//            String storedPassword = resultSet.getString("password");
//            if (!passwordEncoder.matches(password, storedPassword)) {
//                Map<String, String> map = new HashMap<>();
//                map.put("error_message", "用户名或密码错误");
//                return map;
//            }
            System.out.println("User ID: " + resultSet.getInt("id"));
            System.out.println("Username: " + resultSet.getString("username"));
            Integer id = resultSet.getInt("id");
            System.out.println(resultSet.getInt("id"));
            jwt = JwtUtil.createJWT(id.toString());

            // Close the connection

            connection.close();
        } catch (SQLException e) {
            System.out.println("Cannot connect to the database");
            e.printStackTrace();
        }

//        String jwt = JwtUtil.createJWT(user.getId().toString());
        System.out.println("Password matches!");
        Map<String, String> map = new HashMap<>();
        map.put("error_message" ,"success");
        map.put("token", jwt);

        return map;
    }
}
