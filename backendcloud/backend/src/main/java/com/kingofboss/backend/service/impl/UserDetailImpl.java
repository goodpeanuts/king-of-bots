package com.kingofboss.backend.service.impl;

import com.kingofboss.backend.pojo.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailImpl implements UserDetails {
    // 这里也是按着alt + insert 然后选择实现方法，全部插入

    private User user;  // pojo 中的user
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;   // 没有锁定
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;   // 没有过期
    }

    @Override
    public boolean isEnabled() {
        return true;    // 启用
    }
}
