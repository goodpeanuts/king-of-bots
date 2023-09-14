package com.kingofboss.botrunningsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kingofboss.botrunningsystem.mapper.UserMapper;
import com.kingofboss.botrunningsystem.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    // 补充spring security 组件，实现数据库中的用户能登录
    // 使用数据库不要忘改了加这个@
    @Autowired
    private UserMapper userMapper;

    // 这里按住 alt + insert 键 选择重写函数 loadUserByUsername
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return new UserDetailImpl(user);
    }
}
