package com.siemens.ctc.service;

import com.github.pagehelper.PageInfo;
import com.siemens.ctc.model.UserInfo;

/**
 * @author Fan Yang
 * createTime: 2019-07-22
 */

public interface UserInfoService {
    /**
     * 根据用户名获取用户
     *
     * @param username 用户名
     * @return 用户对象
     */

    UserInfo findByUsername(String username);

    /**
     * 根据邮箱获取用户
     *
     * @param email 邮箱
     * @return 用户对象
     */

    UserInfo findByEmail(String email);

    /**
     * 根据用户id查询用户
     */

    UserInfo findByUserId(Integer userId);

    /**
     * 添加用户
     *
     * @param userInfo 用户对象
     * @return 添加的用户数
     */

    int addUser(UserInfo userInfo);

    /**
     * 删除用户
     *
     * @param email 邮箱
     * @return 删除的用户数
     */

    int deleteUser(String email);

    /**
     * 获取所有用户
     *
     * @param pageNum  页数
     * @param pageSize 单页数量
     * @return json
     */

    PageInfo<UserInfo> findAllUsers(int pageNum, int pageSize);

    /**
     * 根据角色获取用户
     */

    PageInfo<UserInfo> findUsersByRole(String role, int pageNum, int pageSize);

    /**
     * 更新用户密码
     *
     * @param email       邮箱
     * @param newPassword 新密码
     * @return 是否更新
     */

    boolean updatePassword(String email, String newPassword);

    /**
     * 更新用户信息
     *
     * @param email      邮箱
     * @param newRole    新角色
     * @param name       姓名
     * @param department 部门
     * @return 更新的用户数
     */

    int update(String email, String newRole, String name, String department);

    /**
     * 更新用户信息
     */

    int updateUser(UserInfo userInfo);

    /**
     * 判断邮箱是否存在
     *
     * @param email 邮箱
     * @return 是否存在
     */

    boolean isEmailExist(String email);
}
