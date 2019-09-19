package com.siemens.ctc.dao;

import com.siemens.ctc.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * UserInfo Dao
 *
 * @author Fan Yang
 * createTime: 2019-07-19
 */

@Repository
@Mapper
public interface UserInfoMapper {

    /**
     * 通过用户名获取用户对象
     *
     * @param username 用户名
     * @return 用户对象
     */

    UserInfo findByUsername(@Param("username") String username);

    /**
     * 根据用户id获取用户
     */
    UserInfo findByUserId(@Param("userId") Integer userId);

    /**
     * 添加用户
     *
     * @param userInfo 用户对象
     * @return 增加量
     */
    int addUser(UserInfo userInfo);

    /**
     * 删除用户
     *
     * @param email 邮箱
     * @return 删除量
     */
    int deleteUser(@Param("email") String email);

    /**
     * 通过邮箱获取用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    UserInfo findByEmail(@Param("email") String email);

    /**
     * 获取所有用户
     *
     * @return 用户对象列表
     */
    List<UserInfo> findAllUsers();

    /**
     * 根据角色获取用户
     */
    List<UserInfo> findUserByRole(@Param("role") String role);

    /**
     * 更新用户密码
     *
     * @param email       邮箱
     * @param newPassword 新密码
     * @return 是否更新
     */
    boolean updatePassword(@Param("email") String email,
                           @Param("newPassword") String newPassword);

    /**
     * 更新用户信息
     *
     * @param email      邮箱
     * @param newRole    新角色
     * @param name       名字
     * @param department 部门
     * @return 更新量
     */
    int update(@Param("email") String email,
               @Param("newRole") String newRole,
               @Param("name") String name,
               @Param("department") String department);

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
    boolean isEmailExist(@Param("email") String email);
}
