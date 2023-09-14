package com.kingofboss.botrunningsystem.consumer.utils;

import com.kingofboss.botrunningsystem.utils.JwtUtil;
import io.jsonwebtoken.Claims;

public class JwtAuthentication {
    public static Integer getUserId(String toekn) {
        int userId = -1;
        try {
            Claims claims = JwtUtil.parseJWT(toekn);
            userId = Integer.parseInt(claims.getSubject());
        }   catch (Exception e) {
            throw new RuntimeException(e);
        }
        return userId;

    }
}
