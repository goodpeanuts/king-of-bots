package com.kingofboss.backend.service.user.account;

import java.util.Map;

public interface ResetPasswordService {
    public Map<String, String> reset(String password, String confirmPassword);
}
