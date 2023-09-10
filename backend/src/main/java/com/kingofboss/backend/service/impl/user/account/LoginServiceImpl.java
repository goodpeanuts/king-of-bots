package com.kingofboss.backend.service.impl.user.account;

import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.account.LoginService;
import com.kingofboss.backend.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

// 实现service此处添加注解
@Service
public class LoginServiceImpl implements LoginService {
    // 此处按住alt + insert 自动生成实现


    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Map<String, String> getToken(String username, String password) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        // 在authenticate 后输入 .var 自动生成变量名与类型
        Authentication authenticate = authenticationManager.authenticate(authenticationToken); // 登录失败会自动处理

        UserDetailImpl loginUser = (UserDetailImpl) authenticate.getPrincipal();
        User user = loginUser.getUser();
        // 这里getId() 与课上不同
        String jwt = JwtUtil.createJWT(user.getId().toString());

        Map<String, String> map = new HashMap<>();
        map.put("error_message" ,"success");
        map.put("token", jwt);

        return map;
    }
}
