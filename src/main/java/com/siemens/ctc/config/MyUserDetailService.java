package com.siemens.ctc.config;

import com.siemens.ctc.controller.MediaController;
import com.siemens.ctc.model.UserInfo;
import com.siemens.ctc.dao.UserInfoMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

public class MyUserDetailService implements UserDetailsService {

    private static final Logger LOGGER = LogManager.getLogger(MediaController.class);

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 从数据库获取用户
        UserInfo user = userInfoMapper.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("该用户不存在");
        }

        LOGGER.info("username: " + user.getUsername() + ";password: " + user.getPassword());
        LOGGER.info("role: " + user.getRole());

        // 获取用户的角色
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));

        return new User(
                user.getUsername(),
                new BCryptPasswordEncoder().encode(user.getPassword()),
                authorities
        );
    }
}
