package com.kingofboss.backend.controller.user.account;

import com.kingofboss.backend.pojo.User;
import com.kingofboss.backend.service.impl.UserDetailImpl;
import com.kingofboss.backend.service.user.account.ResetPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ResetPasswordController {
    @Autowired
    private ResetPasswordService resetPasswordService;

    @PostMapping("/api/user/account/reset/")
    public Map<String, String> register(@RequestParam Map<String, String> map) {

        String password = map.get("password");
        String confirmPassword = map.get("confirmPassword");
        return  resetPasswordService.reset(password, confirmPassword);
    }
}
