package com.siemens.ctc.service;

import com.siemens.ctc.model.UserInfo;

/**
 * Auto Service层接口
 */

public interface AuthService {
    UserInfo register(UserInfo userToAdd);

    String login(String username, String password);

    String refresh(String oldToken);
}
