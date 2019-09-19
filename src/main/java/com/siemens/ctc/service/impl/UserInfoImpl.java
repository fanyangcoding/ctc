package com.siemens.ctc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.siemens.ctc.dao.UserInfoMapper;
import com.siemens.ctc.model.UserInfo;
import com.siemens.ctc.service.UserInfoService;
import com.siemens.ctc.tools.exception.BusinessException;
import com.siemens.ctc.tools.exception.ErrorCodeEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Fan Yang
 * createTime: 2019-07-25
 */

@Service
public class UserInfoImpl implements UserInfoService {

    private static Logger LOGGER = LogManager.getLogger(UserInfoImpl.class);

    @Resource
    private UserInfoMapper userInfoMapper;

    /**
     * 通过用户名查找用户
     *
     * @param username 用户名
     * @return 用户对象
     */

    @Override
    public UserInfo findByUsername(String username) {
        UserInfo userInfo = userInfoMapper.findByUsername(username);
//        if (userInfo == null) {
//            LOGGER.error("该用户不存在");
//            throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
//        }
        return userInfo;
    }

    /**
     * 根据邮箱查找用户
     *
     * @param email 邮箱
     * @return 用户对象
     */

    @Override
    public UserInfo findByEmail(String email) {
        if (!userInfoMapper.isEmailExist(email)) {
            LOGGER.error("该邮箱不存在");
            throw new BusinessException(ErrorCodeEnum.EMAIL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_NOT_EXIST.getMsg());
        }
        return userInfoMapper.findByEmail(email);
    }

    /**
     * 根据用户id查询用户
     */
    @Override
    public UserInfo findByUserId(Integer userId) {
        return userInfoMapper.findByUserId(userId);
    }

    /**
     * 添加用户
     *
     * @param userInfo 用户对象
     * @return 添加用户的数量
     */

    @Override
    public int addUser(UserInfo userInfo) {
        if (userInfo == null) {
            LOGGER.error("改用户不存在");
            throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        }
        int count = userInfoMapper.addUser(userInfo);
        if (count == 0) {
            LOGGER.error("添加用户失败");
            throw new BusinessException(ErrorCodeEnum.ADD_USER_FAILURE.getErrorCode(), ErrorCodeEnum.ADD_USER_FAILURE.getMsg());
        } else {
            return count;
        }
    }

    /**
     * 删除用户
     *
     * @param email 邮箱
     * @return 删除用户的数量
     */

    @Override
    public int deleteUser(String email) {
        if (!userInfoMapper.isEmailExist(email)) {
            LOGGER.error("该邮箱不存在");
            throw new BusinessException(ErrorCodeEnum.EMAIL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_NOT_EXIST.getMsg());
        }
        int count = userInfoMapper.deleteUser(email);
        if (count == 0) {
            LOGGER.error("删除用户失败");
            throw new BusinessException(ErrorCodeEnum.DELETE_USER_FAILURE.getErrorCode(), ErrorCodeEnum.DELETE_USER_FAILURE.getMsg());
        }
        return count;
    }

    /**
     * 更新用户信息
     *
     * @param email      邮箱
     * @param newRole    新角色
     * @param name       姓名
     * @param department 部门
     * @return 更新的用户数量
     */

    @Override
    public int update(String email, String newRole, String name, String department) {
        if (!userInfoMapper.isEmailExist(email)) {
            LOGGER.error("该邮箱不存在");
            throw new BusinessException(ErrorCodeEnum.EMAIL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_NOT_EXIST.getMsg());
        }
        int count = userInfoMapper.update(email, newRole, name, department);
        if (count == 0) {
            LOGGER.error("g更新用户信息失败");
            throw new BusinessException(ErrorCodeEnum.UPDATE_USER_FAILURE.getErrorCode(), ErrorCodeEnum.UPDATE_USER_FAILURE.getMsg());
        }
        return count;
    }

    /**
     * 更新用户信息
     */
    @Override
    public int updateUser(UserInfo userInfo) {
        if (userInfo == null) {
            throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        }
        int count = userInfoMapper.updateUser(userInfo);
        if (count == 0) {
            throw new BusinessException(ErrorCodeEnum.USER_NULL.getErrorCode(), ErrorCodeEnum.USER_NULL.getMsg());
        }
        return count;
    }

    /**
     * 判断邮箱是否存在
     *
     * @param email 邮箱
     * @return true or false
     */

    @Override
    public boolean isEmailExist(String email) {
        boolean isExist = userInfoMapper.isEmailExist(email);
        return isExist;
    }

    /**
     * 更新用户密码
     *
     * @param email       邮箱
     * @param newPassword 新密码
     * @return true or false
     */
    @Override
    public boolean updatePassword(String email, String newPassword) {
        if (!userInfoMapper.isEmailExist(email)) {
            LOGGER.error("该邮箱不存在");
            throw new BusinessException(ErrorCodeEnum.EMAIL_NOT_EXIST.getErrorCode(), ErrorCodeEnum.EMAIL_NOT_EXIST.getMsg());
        }
        boolean updateSuccess = userInfoMapper.updatePassword(email, newPassword);
        if (!updateSuccess) {
            LOGGER.error("密码更新失败");
            throw new BusinessException(ErrorCodeEnum.PASSWORD_UPDATE_FAILURE.getErrorCode(), ErrorCodeEnum.PASSWORD_UPDATE_FAILURE.getMsg());
        }
        LOGGER.info("密码更新成功");
        return true;
    }

    /**
     * 查找所有用户
     *
     * @param pageNum  页数
     * @param pageSize 单页数量
     * @return json
     */

    @Override
    public PageInfo<UserInfo> findAllUsers(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> userList = userInfoMapper.findAllUsers();
        return new PageInfo<>(userList);
    }

    /**
     * 根据角色获取用户
     */
    @Override
    public PageInfo<UserInfo> findUsersByRole(String role, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<UserInfo> userList = userInfoMapper.findUserByRole(role);
        return new PageInfo<>(userList);
    }
}
