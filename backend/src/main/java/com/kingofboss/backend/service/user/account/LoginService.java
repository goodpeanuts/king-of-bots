package com.kingofboss.backend.service.user.account;

import java.util.Map;

public interface LoginService {
    public Map<String, String> login(String username, String password);
}
